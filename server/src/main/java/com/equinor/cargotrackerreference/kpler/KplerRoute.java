package com.equinor.cargotrackerreference.kpler;

import javax.annotation.Resource;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KplerRoute extends RouteBuilder {
	
	@Autowired
	KplerProcessor kplerProcessor;
	
	@Resource(name = "tradeJsonDataFormat")
	private JacksonDataFormat tradeJsonDataFormat; //Bean defined in KplerConfig

    @Override
    public void configure() throws Exception {    	
        from("timer:foo?fixedRate=true&period=60000")
        .routeId("Kpler")        
        .setBody(simple("select * from selfservice.raw_kpler_lpg_trades2"))        
		.to("jdbc:kplerDataSource")
		.log(LoggingLevel.INFO, getClass().getPackage().getName(), "Found ${header.CamelJdbcRowCount} rows.")
		.split(body())			
			.streaming()
			.log(LoggingLevel.INFO, getClass().getPackage().getName(), "Converting to json ${body}")
			.marshal(tradeJsonDataFormat)
			.log(LoggingLevel.INFO, getClass().getPackage().getName(), "After marshal: ${body}")
			.unmarshal(tradeJsonDataFormat)
			.process(kplerProcessor)
			.stop();
    }
}