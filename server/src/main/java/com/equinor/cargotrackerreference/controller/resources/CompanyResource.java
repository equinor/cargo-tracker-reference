package com.equinor.cargotrackerreference.controller.resources;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.equinor.cargotrackerreference.controller.resources.analyticscargoresource.IdNameProperty;

@Entity
@Table(name="COMPANY")
@Immutable
public class CompanyResource extends IdNameProperty {
	
	public int version;
	public boolean verified;
	public boolean cancelled;
	public String shortName;

	public CompanyResource(UUID id, String name) {
		super(id, name);
	}
	public CompanyResource() {
		
	}
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="COMPANY_OCD_MAPPING", 
					 joinColumns=@JoinColumn(name="COMPANY_ID"))
	@Column(name="OCD_NAME", insertable=false, updatable=false)
	public Set<String> aliases;
}
