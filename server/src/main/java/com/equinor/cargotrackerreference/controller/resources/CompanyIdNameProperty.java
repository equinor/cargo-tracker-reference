package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="COMPANY")
@Immutable
public class CompanyIdNameProperty extends IdNameProperty {

	public String shortName;
	
	public CompanyIdNameProperty(UUID id, String name, String shortName) {
		super(id, name);
		this.shortName = shortName;
	}

	public CompanyIdNameProperty() {
	}

}
