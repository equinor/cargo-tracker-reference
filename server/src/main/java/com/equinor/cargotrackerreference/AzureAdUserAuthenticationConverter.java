package com.equinor.cargotrackerreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

public class AzureAdUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
	
	final Map<String, String> appRegRoleToAppRole = 
			Map.of("CrudeCargoTrackerUser", "ROLE_USER",
				   "CrudeCargoTrackerReadOnlyUser", "ROLE_READ_ONLY_USER",
				   "CrudeCargoTrackerSuperUser", "ROLE_SUPER_USER");
	
	private Collection<? extends GrantedAuthority> defaultAuthorities;

	private UserDetailsService userDetailsService;

	/**
	 * Optional {@link UserDetailsService} to use when extracting an {@link Authentication} from the incoming map.
	 * 
	 * @param userDetailsService the userDetailsService to set
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	public Authentication extractAuthentication(Map<String, ?> map) {
		if (map.containsKey("upn") || map.containsKey("preferred_username")) {				
			String upn = map.containsKey("upn") ? map.get("upn").toString() :  map.get("preferred_username").toString();
			Object principal = upn.substring(0, upn.indexOf("@"));
			Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
			if (userDetailsService != null) {
				UserDetails user = userDetailsService.loadUserByUsername((String) map.get(USERNAME));
				authorities = user.getAuthorities();
				principal = user;
			}
			return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
		}
		return null;
	}	
	
	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		if (!map.containsKey("roles")) {
			return defaultAuthorities;
		}
		Object authorities = map.get("roles");			
		if (authorities instanceof String) {
			String mappedAuthority = appRegRoleToAppRole.get((String)authorities);
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) mappedAuthority);
		}
		if (authorities instanceof Collection) {
			List<String> mappedAuthorities = new ArrayList<String>();
			for (Object auth : (Collection)authorities) {
				mappedAuthorities.add(appRegRoleToAppRole.get((String)auth));
			}
			return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
					.collectionToCommaDelimitedString((Collection<?>) mappedAuthorities));
		}
		throw new IllegalArgumentException("Authorities must be either a String or a Collection");
	}
}