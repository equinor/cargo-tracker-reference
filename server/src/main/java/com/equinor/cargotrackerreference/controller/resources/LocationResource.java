package com.equinor.cargotrackerreference.controller.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class LocationResource {
	public UUID id;
	public String name;
	public BigDecimal latitude;
	public BigDecimal longitude;
	public boolean cancelled;
	public Integer version;
	public LocalDateTime updatedDateTime;
	public String updatedBy;

	@Override
	public String toString() {
		return "LocationResource [id=" + id + ", name=" + name + "]";
	}
	
	
}
