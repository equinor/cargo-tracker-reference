package com.equinor.cargotrackerreference;

import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/*
 * This configuration is used when Mocking a SecurityContext with the @WithUserDetails annotation.  
 * The endpoint security configuration is set up in the ResourceServerConfigurerAdapter (HttpSecurity)
 */
@Configuration
public class SecurityTestConfiguration {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Bean
	@Primary
	public UserDetailsService userDetailsService() {

		UserDetails basicUser = User.builder().
				username("user@equinor.com").
				authorities("ROLE_USER").
				password("password").build();
		
		UserDetails readOnlyUser = User.builder().
				username("readonlyuser@equinor.com").
				authorities("ROLE_READ_ONLY_USER").
				password("password").build();

		UserDetails superUser = User.builder().
				username("superuser@equinor.com").
				authorities("ROLE_SUPER_USER").
				password("password").build(); 

		UserDetails adminUser = User.builder().
				username("admin@equinor.com").
				authorities("ROLE_ADMIN").
				password("password").build();
		
		UserDetails noAccessUser = User.builder().
				username("noaccess@equinor.com").
				authorities("ROLE_FOO").
				password("password").build(); 

		return new InMemoryUserDetailsManager(Arrays.asList(
				basicUser, superUser, readOnlyUser, noAccessUser, adminUser
				));       
	}
	
	/**
	 * We need to set the OAuth2AuthenticationProcessingFilter to stateful after doing the springSecurity() setup 
	 * otherwise it will clear the mocked Security Context
	 * 
	 * @return Configured MockMvc
	 */
	@Bean
	public MockMvc mockMvc() {
		MockMvc mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
			
		FilterChainProxy chain = (FilterChainProxy) webApplicationContext.getBean("springSecurityFilterChain");

		List<Filter> filters = chain.getFilters("/");
		for (Filter filter : filters) {
			if (filter instanceof OAuth2AuthenticationProcessingFilter) {
				((OAuth2AuthenticationProcessingFilter)filter).setStateless(false);
				break;
			}
		}
		
		return mockMvc;
	}
}
