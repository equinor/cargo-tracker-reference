package com.equinor.cargotrackerreference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;

@SpringBootApplication
@Configuration
@EntityScan(basePackages = {"com.equinor.cargotracker.common.domain", 
		"com.equinor.cargotrackerreference.domain", 
		"com.equinor.cargotrackerreference.controller.resources"})
public class Application {


	public static void main(String[] args) {
				
		if (FirsttimeSqlDbSetup.validInitPrecond()) {
			FirsttimeSqlDbSetup.verify();
		}
		
		SpringApplication app = new SpringApplication(Application.class);				
		app.run(args);		
	}	
		
	@Bean
	public FilterRegistrationBean<WebRequestTrackingFilter> appInsightsFilter(){
	    FilterRegistrationBean<WebRequestTrackingFilter> registrationBean = new FilterRegistrationBean<>();
	         
	    registrationBean.setFilter(new WebRequestTrackingFilter("CargoTrackerReference"));	    
	    registrationBean.addUrlPatterns("/*");	    
	    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
	    return registrationBean;    
	}
	
}
