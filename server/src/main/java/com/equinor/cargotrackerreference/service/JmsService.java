package com.equinor.cargotrackerreference.service;

import java.util.Arrays;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

import com.equinor.cargotracker.common.utils.Constants;
import com.equinor.cargotrackerreference.config.AzureServiceBusConfiguration;
import com.microsoft.applicationinsights.TelemetryClient;

@Service
public class JmsService {
	
	@Value("${servicebus.connection_uri}")
	private String connectionUri;
	
	@Value("${servicebus.key_name}")
	private String keyName;
	
	@Value("${servicebus.key}")
	private String key;
		
	@Value("${servicebus.topic}")	
	private String topic;
	
	@Resource(name = "jacksonJmsMessageConverter")
	private MappingJackson2MessageConverter jacksonJmsMessageConverter;

	@Autowired
	private Environment env;

	@Autowired
	private TelemetryClient telemetry;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	public void sendJmsMessage(Object message, String typeid, String operation) {
		/*
		 * Disabling for Unittests
		 */
		if (Arrays.asList(env.getActiveProfiles()).contains("h2")) {
			return;
		}
		telemetry.trackEvent(
				Constants.CARGO_TRACKER_REFERENCE_SHORT + " sending message to " + Constants.CRUDE_CARGO_TRACKER_SHORT);
		try {

			// Adding properties needed for the qpid library
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
			props.put("connectionfactory.myFactoryLookup", connectionUri);
			props.put("topic.myTopicLookup", topic);

			Context context = new InitialContext(props);

			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("myFactoryLookup");
			Connection connection = connectionFactory.createConnection(keyName, key);
			connection.start();

			Topic serviceBusTopic = (Topic) context.lookup("myTopicLookup");

			// Create sender-side Session and MessageProducer
			Session sendSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = sendSession.createProducer(serviceBusTopic);

			Message jmsTextMessage = jacksonJmsMessageConverter.toMessage(message, sendSession);

			// Set an extra property telling what type of operation for the object
			jmsTextMessage.setStringProperty("operation", operation);

			logger.debug("Sending message to:\ntopic: {}\ntype: {}\noperation: {}\npayload: {}",
					messageProducer.getDestination(),
					jmsTextMessage.getStringProperty(AzureServiceBusConfiguration.TYPEID), operation,
					jmsTextMessage.getBody(String.class));

			messageProducer.send(jmsTextMessage);
			jmsTextMessage.acknowledge();

			sendSession.close();
			connection.close();
		} catch (Exception e) {
			telemetry.trackEvent(Constants.CARGO_TRACKER_REFERENCE_SHORT + " failed sending message to "
					+ Constants.CRUDE_CARGO_TRACKER_SHORT);
			logger.error("Failed sending message to " + Constants.CRUDE_CARGO_TRACKER, e);
		}
	}
}
