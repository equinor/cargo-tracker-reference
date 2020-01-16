package com.equinor.cargotrackerreference.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "region")
public class Region extends VersionedEntityName {

	private boolean active;

	public Region() {
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
