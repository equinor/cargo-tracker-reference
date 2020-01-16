package com.equinor.cargotrackerreference.domain;

import java.util.UUID;

import javax.persistence.Entity;

@Entity
public class Country extends Location {

	private String regionId;

	public Country() {
		super();
	}

	public Country(UUID id) {
		super(id);
	}
	
	public UUID getRegionId() {
		return regionId != null ?  UUID.fromString(regionId) : null;
	}

	public void setRegionId(UUID regionId) {
		this.regionId = regionId != null ? regionId.toString() : null;
	}
}
