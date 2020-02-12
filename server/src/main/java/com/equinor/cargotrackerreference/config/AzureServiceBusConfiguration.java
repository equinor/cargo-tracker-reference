package com.equinor.cargotrackerreference.config;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.equinor.cargotrackerreference.controller.resources.CompanyResource;

/**
 * 
 * Establish a JMS connection and create a topic listener to the Azure Service Bus and 
 * 
 * @author LMID
 *
 */
@Configuration
@Profile("servicebus")
public class AzureServiceBusConfiguration {
	
	@Value("${servicebus.connection_uri}")
	private String connectionUri;
	
	@Value("${servicebus.key_name}")
	private String keyName;
	
	@Value("${servicebus.key}")
	private String key;
	
	@Value("${servicebus.client_id}")
	private String clientId;
	
	public static String TYPEID = "type_id";
	
	//Known message type names that should match the ones sent from the DCF Router	
	public static final String COMPANY_TYPE = "company";
	
	
	//A map containing the different json classes to send and receive the payloads of the jms messages
	private Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();	
	
	public AzureServiceBusConfiguration() {
		typeIdMappings.put(COMPANY_TYPE, CompanyResource.class);
	}
	
	@Bean
	public ConnectionFactory cargoTrackerJmsConnectionFactory() throws RuntimeException {
		JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory(connectionUri);
        jmsConnectionFactory.setUsername(keyName);
        jmsConnectionFactory.setPassword(key);
        jmsConnectionFactory.setClientID(clientId);
        jmsConnectionFactory.setReceiveLocalOnly(true);
        return new CachingConnectionFactory(jmsConnectionFactory);
	}
	
	@Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(TYPEID);
        converter.setTypeIdMappings(typeIdMappings);
        return converter;
    }
	
	//Cargo Tracker message producer	
	@Bean(name="cargoTrackerJmsTemplate")
	public JmsTemplate cargoTrackerJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setDefaultDestinationName("cargotrackers");
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
		jmsTemplate.setConnectionFactory(cargoTrackerJmsConnectionFactory());
		return jmsTemplate;
	}
}
