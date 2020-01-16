package com.equinor.cargotrackerreference.controller.resources;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

public class TerminalResource extends LocationResource {
	public UUID countryId;
	public String source;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="TERMINAL_OCD_MAPPING", joinColumns=@JoinColumn(name="TERMINAL_ID"))
	@Column(name="OCD_NAME", insertable=false, updatable=false)
	public Set<String> aliases;

	public TerminalResource() {
		super();
	}

	@Override
	public String toString() {
		return super.toString() + " TerminalResource [countryId=" + countryId + ", source=" + source + "]";
	}
	
	

}
