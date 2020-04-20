package com.equinor.cargotrackerreference.service;

import java.util.Arrays;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
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
	
	@Value("${servicebus.topic_key}")
	private String topicKey;
	
	@Value("${servicebus.queue_key}")
	private String queueKey;
		
	@Value("${servicebus.topic}")	
	private String topic;
	
	@Value("${servicebus.queue}")	
	private String queue;
	
	@Resource(name = "jacksonJmsMessageConverter")
	private MappingJackson2MessageConverter jacksonJmsMessageConverter;

	@Autowired
	private Environment env;

	@Autowired
	private TelemetryClient telemetry;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	public void sendJmsTopicMessage(Object message, String typeid, String operation) {
		sendJmsMessage(message, typeid, operation, false);
	}
	
	public void sendJmsQueueMessage(Object message, String typeid, String operation) {
		sendJmsMessage(message, typeid, operation, true);
	}
	
	public void sendJmsMessage(Object message, String typeid, String operation, boolean toQueue) {
		/*
		 * Disabling for Unittests
		 */
		if (Arrays.asList(env.getActiveProfiles()).contains("h2")) {
			return;
		}
		
		String key = null;
		if(toQueue) {
			key = queueKey;
		}
		else {
			key = topicKey;
		}

		try {

			// Adding properties needed for the qpid library
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
			props.put("connectionfactory.myFactoryLookup", connectionUri);
			props.put("queue.myQueueLookup", queue);
			props.put("topic.myTopicLookup", topic);
			

			Context context = new InitialContext(props);

			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("myFactoryLookup");
			Connection connection = connectionFactory.createConnection(keyName, key);
			connection.start();

			Topic serviceBusTopic = (Topic) context.lookup("myTopicLookup");
			Queue serviceBusQueue = (Queue) context.lookup("myQueueLookup");

			// Create sender-side Session and MessageProducer
			Session sendSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = null;
			if(toQueue) {
				messageProducer = sendSession.createProducer(serviceBusQueue);
			}
			else {
				messageProducer = sendSession.createProducer(serviceBusTopic);
			}

			Message jmsTextMessage = jacksonJmsMessageConverter.toMessage(message, sendSession);

			// Set an extra property telling what type and type of operation for the object
			jmsTextMessage.setStringProperty(AzureServiceBusConfiguration.TYPEID, typeid);
			
			// Set an extra property telling what type of operation for the object
			jmsTextMessage.setStringProperty("operation", operation);

			
			
			String busType = "queue";
			if(!toQueue) {
				busType = "topic";
			}
			
			logger.debug("Sending message to:\n{}: {}\ntype: {}\noperation: {}\npayload: {}",
					busType,
					messageProducer.getDestination(),
					jmsTextMessage.getStringProperty(AzureServiceBusConfiguration.TYPEID), operation,
					jmsTextMessage.getBody(String.class));

			messageProducer.send(jmsTextMessage);
			jmsTextMessage.acknowledge();

			sendSession.close();
			connection.close();
		} catch (Exception e) {
			telemetry.trackEvent(Constants.CARGO_TRACKER_REFERENCE_SHORT + " - " + Constants.FAILED + " sending message.");
			logger.error("Failed sending message to " + Constants.CRUDE_CARGO_TRACKER, e);
		}
		telemetry.trackEvent(Constants.CARGO_TRACKER_REFERENCE_SHORT + " - " + Constants.SUCCESS + " sending message.");
	}
}
