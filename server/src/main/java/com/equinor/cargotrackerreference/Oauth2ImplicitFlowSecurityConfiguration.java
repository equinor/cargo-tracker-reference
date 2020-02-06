package com.equinor.cargotrackerreference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

/**
 * Security setup for OAuth2
 *
 *
 */
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Profile("!migrationclient")
public class Oauth2ImplicitFlowSecurityConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${security.oauth2.resource.id}")
	private String resourceId;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.headers()
				.frameOptions()
				.sameOrigin() /* Needed for ADAL token refresh */
				.and()
				.authorizeRequests() // TODO HEH: Verify setup now that ctref is introduced
				.antMatchers(HttpMethod.DELETE, "/ctref/config/**").access("hasRole('SUPER_USER')")
				.antMatchers(HttpMethod.POST, "/ctref/config/**").access("hasRole('SUPER_USER')")
				.antMatchers(HttpMethod.PUT, "/ctref/config/**").access("hasRole('SUPER_USER')")
				.antMatchers(HttpMethod.PATCH, "/ctref/config/**").access("hasRole('SUPER_USER')")
				.antMatchers(HttpMethod.GET, "/ctref/config/**").access("hasRole('READ_ONLY_USER') or hasRole('USER') or hasRole('SUPER_USER')")
				/*.antMatchers(HttpMethod.DELETE, "/ct/**").access("hasRole('USER') or hasRole('SUPER_USER')")
				.antMatchers(HttpMethod.POST, "/ct/**").access("hasRole('USER') or hasRole('SUPER_USER')")
				.antMatchers(HttpMethod.PUT, "/ct/**").access("hasRole('USER') or hasRole('SUPER_USER')")
				.antMatchers(HttpMethod.PATCH, "/ct/**").access("hasRole('USER') or hasRole('SUPER_USER')")
				.antMatchers(HttpMethod.GET, "/ct/**").access("hasRole('READ_ONLY_USER') or hasRole('USER') or hasRole('SUPER_USER')")*/
				.antMatchers("/**").permitAll();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceId);
	}

	@Bean
	public TokenStore jwkTokenStore(ResourceServerProperties resource) {
		AzureAdUserAuthenticationConverter authenticationConverter = new AzureAdUserAuthenticationConverter();
		DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
		tokenConverter.setUserTokenConverter(authenticationConverter);
		return new JwkTokenStore(resource.getJwk().getKeySetUri(), tokenConverter);
	}

}
