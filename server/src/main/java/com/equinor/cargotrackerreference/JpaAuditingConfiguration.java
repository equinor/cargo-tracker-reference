package com.equinor.cargotrackerreference;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

	@Bean
	AuditorAware<String> auditorProvider() {
		return new AuditorAware<String>() {

			@Override
			public Optional<String> getCurrentAuditor() {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth == null) {
					throw new RuntimeException("No security context");
				} else {
					String name = auth.getName();
					if (name.contains("@")) {
						name = name.substring(0, name.indexOf("@"));
					}
					return Optional.of(name);
				}
			}
			
		};
	}
	
}
