package com.equinor.cargotrackerreference.builder;

import java.time.LocalDateTime;

import com.equinor.cargotracker.common.domain.TradingArea;

public class TradingAreaBuilder {

	private String name;
	private boolean active;
	private LocalDateTime updatedDateTime;
	private String updatedBy;

	private TradingAreaBuilder() {
		name = "WAF";
		active = true;
		updatedDateTime = LocalDateTime.now();
		updatedBy = "HLOLS";

	}

	public static TradingAreaBuilder aTradingArea() {
		TradingAreaBuilder regionBuilder = new TradingAreaBuilder();
		return regionBuilder;
	}

	public TradingAreaBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public TradingAreaBuilder withIsActive(boolean active) {
		this.active = active;
		return this;
	}

	public TradingAreaBuilder withUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public TradingAreaBuilder withUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
		return this;
	}

	public TradingArea buildTradingArea() {
		TradingArea region = new TradingArea();
		region.setName(name);
		region.setActive(active);
		region.setUpdatedBy(updatedBy);
		region.setUpdatedDateTime(updatedDateTime);
		return region;
	}
}
