package com.equinor.cargotrackerreference.kpler;

import javax.sql.DataSource;

import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KplerConfig {

	@Value("${kpler.datasource.driverClassName}")
	private String driverClassName;
	
	@Value("${kpler.datasource.url}")
	private String url;
	
	@Value("${kpler.datasource.username}")
	private String userName;
	
	@Value("${kpler.datasource.password}")
	private String passWord;
		
	/*
	@Bean(name = "kplerDataSource", destroyMethod = "")
	public DataSource kplerDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.url(url);
		dataSourceBuilder.username(userName);
		dataSourceBuilder.password(passWord);
		return dataSourceBuilder.build();
	}*/
	
	@Bean(name="tradeJsonDataFormat")
	public JacksonDataFormat tradeJsonDataFormat() {
		JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();		
		jacksonDataFormat.setUnmarshalType(StrippedTrade.class);
		jacksonDataFormat.setAllowUnmarshallType(true);
		//jacksonDataFormat.useList();
		return jacksonDataFormat;		
	}

}
