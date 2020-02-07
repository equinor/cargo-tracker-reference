package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity	
@Table(name="REGION")
@Immutable
public class RegionIdNameProperty extends IdNameProperty {

	public RegionIdNameProperty(UUID id, String name) {
		super(id, name);
	}

	public RegionIdNameProperty() {
	}

}
