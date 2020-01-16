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
public class Company extends VersionedEntityName {

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@CollectionTable(name = "COMPANY_OCD_MAPPING", joinColumns = @JoinColumn(name = "COMPANY_ID"))
	@Column(name = "OCD_NAME")
	private Set<String> aliases;
	
	private String shortName;

	private boolean verified;
	private boolean cancelled = false;
	
	public Company() {
		super();
		this.aliases = new HashSet<>();
	}
	
	public Company(UUID id) {
		super(id);
		this.aliases = new HashSet<>();
	}
	
	public Company(String name) {
		this();
		this.setName(name);
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

	public boolean isVerified() {
		return verified;
	}
	
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	

}
