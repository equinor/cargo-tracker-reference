package com.equinor.cargotrackerreference.service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import com.equinor.cargotrackerreference.config.AzureServiceBusConfiguration;

@Service
public class JmsService {
	
	@Resource(name = "cargoTrackerJmsTemplate")
    private JmsTemplate cargoTrackerJmsTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
			
	public void sendJmsMessage(Object message, String typeid, String operation) {
	
		// Sends a JMS TextMessage
		cargoTrackerJmsTemplate.convertAndSend(message, new MessagePostProcessor() {			
			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty(AzureServiceBusConfiguration.TYPEID, typeid);
				message.setStringProperty("operation", operation);
				logger.debug("Sending message to:\ntopic: {}\ntype: : {}\npayload: {}", cargoTrackerJmsTemplate.getDefaultDestinationName(), message.getStringProperty(AzureServiceBusConfiguration.TYPEID), message.getBody(String.class));
				
				return message;
			}
						
			
		});
	}
}
