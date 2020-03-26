package com.equinor.cargotrackerreference.controller.resources;

import java.util.Set;
import java.util.UUID;

public class TerminalResource extends LocationResource {
	public UUID countryId;
	public String source;
	
	public Set<String> aliases;

	public TerminalResource() {
		super();
	}

	@Override
	public String toString() {
		return super.toString() + " TerminalResource [countryId=" + countryId + ", source=" + source + "]";
	}
	
	

}
