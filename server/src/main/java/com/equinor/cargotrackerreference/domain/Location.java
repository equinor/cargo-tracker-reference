package com.equinor.cargotrackerreference.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Location extends VersionedEntityName {

	private BigDecimal latitude;
	private BigDecimal longitude;
	private boolean cancelled = false;

	public Location() {
	}
	
	public Location(UUID id) {
		super(id);
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}


	@Override
	public String toString() {
		return super.toString() + " Location [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
