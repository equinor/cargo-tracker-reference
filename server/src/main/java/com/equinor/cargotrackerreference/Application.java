package com.equinor.cargotrackerreference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;

@SpringBootApplication
@Configuration
@EnableScheduling
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
