package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;

public class CompanyIdNameProperty extends IdNameProperty {

	public String shortName;
	
	public CompanyIdNameProperty(UUID id, String name, String shortName) {
		super(id, name);
		this.shortName = shortName;
	}

	public CompanyIdNameProperty() {
	}

}
