package com.equinor.cargotrackerreference.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Terminal extends Location {

	private String countryId;
	private String source;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@CollectionTable(name = "TERMINAL_OCD_MAPPING", joinColumns = @JoinColumn(name = "TERMINAL_ID"))
	@Column(name = "OCD_NAME")
	private Set<String> aliases;

	public Terminal() {
		this.aliases = new HashSet<>();
	}
	
	public Terminal(UUID id) {
		super(id);
		this.aliases = new HashSet<>();
	}

	public UUID getCountryId() {
		return countryId != null ? UUID.fromString(countryId) : null;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId != null ? countryId.toString() : null;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public Set<String> getAliases() {
		return aliases;
	}

	public void setAliases(Set<String> aliases) {
		this.aliases = aliases;
	}
	
	public void appendAlias(String alias) {
		aliases.add(alias);
	}
}
