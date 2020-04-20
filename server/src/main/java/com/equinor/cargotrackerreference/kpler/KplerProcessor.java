package com.equinor.cargotrackerreference.kpler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equinor.cargotrackerreference.config.AzureServiceBusConfiguration;
import com.equinor.cargotrackerreference.service.JmsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KplerProcessor implements Processor {
	
	@Autowired
	JmsService jmsService;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Trade trade =  (Trade) exchange.getIn().getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		
		jmsService.sendJmsMessage(mapper.writeValueAsString(trade), AzureServiceBusConfiguration.TRADE_TYPE, "update", true);
	}
}
