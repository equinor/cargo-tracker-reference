package com.equinor.cargotrackerreference;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorityHelper {
	public static boolean isSuperUser() {
		boolean authorized = false;
		var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if ("ROLE_SUPER_USER".equals(grantedAuthority.getAuthority())) {
				authorized = true;
			}
		}
		return authorized;
	}
 
}