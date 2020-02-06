package com.equinor.cargotrackerreference.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.equinor.cargotracker.common.domain.Refinery;

public class RefineryBuilder {

	private String location;
	private String owner;
	private String name;
	private String comments;
	private String regionId;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private LocalDateTime updatedDateTime;
	private String updatedBy;

	private RefineryBuilder() {
		location = "EUROPE";
		owner = "Equinor";
		name = "Refinery1";
		comments = "Refinery in use";
		latitude = new BigDecimal(33.532);
		longitude = new BigDecimal(1.2878);
		updatedDateTime = LocalDateTime.now();
		updatedBy = "HLOLS";
	}

	public static RefineryBuilder aRefinery() {
		RefineryBuilder refineryBuilder = new RefineryBuilder();
		return refineryBuilder;
	}

	public RefineryBuilder withLocation(String location) {
		this.location = location;
		return this;
	}

	public RefineryBuilder withOwner(String owner) {
		this.owner = owner;
		return this;
	}

	public RefineryBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public RefineryBuilder withComments(String comments) {
		this.comments = comments;
		return this;
	}

	public RefineryBuilder withLatitude(BigDecimal latitude) {
		this.latitude = latitude;
		return this;
	}

	public RefineryBuilder withLongitude(BigDecimal longitude) {
		this.longitude = longitude;
		return this;
	}

	public RefineryBuilder withRegionId(String regionId) {
		this.regionId = regionId;
		return this;
	}

	public RefineryBuilder withUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public RefineryBuilder withUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
		return this;
	}

	public Refinery buildRefinery() {
		Refinery refinery = new Refinery();
		refinery.setLocation(location);
		refinery.setName(name);
		refinery.setOwner(owner);
		refinery.setComments(comments);
		refinery.setLatitude(latitude);
		refinery.setLongitude(longitude);
		refinery.setRegionId(regionId);
		refinery.setUpdatedBy(updatedBy);
		refinery.setUpdatedDateTime(updatedDateTime);
		return refinery;
	}
}
