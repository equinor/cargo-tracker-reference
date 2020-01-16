package com.equinor.cargotrackerreference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

/**
 * Security setup for the migration client
 *
 */
@Configuration   
@EnableWebSecurity    
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)  
@Profile("migrationclient")
public class MigrationSecurityConfiguration extends WebSecurityConfigurerAdapter {	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(preauthAuthProvider());
	}
	

	@Bean
	public PreAuthenticatedAuthenticationProvider preauthAuthProvider() {
		PreAuthenticatedAuthenticationProvider preauthAuthProvider = new PreAuthenticatedAuthenticationProvider();
	
		AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> dummyUDS = new AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>() {

			@Override
			public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
					throws UsernameNotFoundException {	
				
				String username = token.getName();
				if (username.contains("@")) {
					username = username.substring(0,username.indexOf("@"));
				}
				return User.builder()
						.username(username)
						.password("dummy")
						.authorities("ROLE_SUPER_USER").build();
			}
				
		};
		
		preauthAuthProvider
				.setPreAuthenticatedUserDetailsService(dummyUDS);		
		return preauthAuthProvider;
	}
	
		
	/**
	 * @param authenticationManager
	 * @return
	 */
	@Bean
	public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() throws Exception{
		RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setPrincipalRequestHeader("x-ms-client-principal-name");
		//filter.setCredentialsRequestHeader("x-ms-token-aad-id-token");		
		return filter;
	}	
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {  
	  http.authorizeRequests()
	    .antMatchers("/**").authenticated()
	    .and()
	    .sessionManagement()
	    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    .and().securityContext()
	    .and()
	    .addFilterBefore(requestHeaderAuthenticationFilter(),LogoutFilter.class).csrf().disable();
	}
	
}
