package com.equinor.cargotrackerreference.builder;

import java.time.LocalDateTime;

import com.equinor.cargotracker.common.domain.Region;

public class RegionBuilder {

	private String name;
	private boolean active;
	private LocalDateTime updatedDateTime;
	private String updatedBy;

	private RegionBuilder() {
		name = "WAF";
		active = true;
		updatedDateTime = LocalDateTime.now();
		updatedBy = "HLOLS";

	}

	public static RegionBuilder aRegion() {
		RegionBuilder regionBuilder = new RegionBuilder();
		return regionBuilder;
	}

	public RegionBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public RegionBuilder withIsActive(boolean active) {
		this.active = active;
		return this;
	}

	public RegionBuilder withUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public RegionBuilder withUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
		return this;
	}

	public Region buildRegion() {
		Region region = new Region();
		region.setName(name);
		region.setActive(active);
		region.setUpdatedBy(updatedBy);
		region.setUpdatedDateTime(updatedDateTime);
		return region;
	}
}
