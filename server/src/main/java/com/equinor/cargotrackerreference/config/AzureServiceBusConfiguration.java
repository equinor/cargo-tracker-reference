package com.equinor.cargotrackerreference.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.equinor.cargotracker.common.domain.Company;
import com.equinor.cargotracker.common.domain.Country;
import com.equinor.cargotracker.common.domain.Grade;
import com.equinor.cargotracker.common.domain.Refinery;
import com.equinor.cargotracker.common.domain.Region;
import com.equinor.cargotracker.common.domain.Terminal;
import com.equinor.cargotracker.common.domain.TradingArea;
import com.equinor.cargotrackerreference.kpler.Trade;

/**
 * 
 * Establish a JMS connection and create a topic listener to the Azure Service Bus and 
 * 
 * @author LMID
 *
 */
@Configuration
public class AzureServiceBusConfiguration {
		
	public static String TYPEID = "type_id";
	
	//Known message type names that should match the ones sent from the DCF Router	
	public static final String COMPANY_TYPE = "company";
	public static final String COUNTRY_TYPE = "country";
	public static final String GRADE_TYPE = "grade";
	public static final String REFINERY_TYPE = "refinery";
	public static final String REGION_TYPE = "region";
	public static final String TERMINAL_TYPE = "terminal";
	public static final String TRADING_AREA_TYPE = "trading_area";
	
	//Kpler message types
	public static final String TRADE_TYPE = "trade";
	
	
	//A map containing the different json classes to send and receive the payloads of the jms messages
	private Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();	
	
	public AzureServiceBusConfiguration() {
		typeIdMappings.put(COMPANY_TYPE, Company.class);
		typeIdMappings.put(COUNTRY_TYPE, Country.class);
		typeIdMappings.put(GRADE_TYPE, Grade.class);
		typeIdMappings.put(REFINERY_TYPE, Refinery.class);
		typeIdMappings.put(REGION_TYPE, Region.class);
		typeIdMappings.put(TERMINAL_TYPE, Terminal.class);
		typeIdMappings.put(TRADING_AREA_TYPE, TradingArea.class);
		
		//Kpler message types
		typeIdMappings.put(TRADE_TYPE, Trade.class);
	}
			
	@Bean(name="jacksonJmsMessageConverter")
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(TYPEID);
        converter.setTypeIdMappings(typeIdMappings);
        return converter;
    }	
}
