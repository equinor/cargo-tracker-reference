package com.equinor.cargotrackerreference.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.equinor.cargotrackerreference.controller.resources.TerminalResource;

public class TerminalBuilder {

	private TerminalResource terminalResource;

	private TerminalBuilder() {
		terminalResource = new TerminalResource();
		terminalResource.name = "Dummy Terminal";
		terminalResource.source = "OCD";
		terminalResource.latitude = new BigDecimal(33.532);
		terminalResource.longitude = new BigDecimal(1.2878);
		terminalResource.countryId = null;
		terminalResource.version = 0;
		terminalResource.updatedDateTime = LocalDateTime.now();
		terminalResource.updatedBy = "HLOLS";
	}

	public static TerminalBuilder aTerminal() {
		TerminalBuilder terminalBuilder = new TerminalBuilder();
		return terminalBuilder;
	}

	public TerminalBuilder withName(String name) {
		terminalResource.name = name;
		return this;
	}

	public TerminalBuilder withSource(String source) {
		terminalResource.source = source;
		return this;
	}

	public TerminalBuilder withCountryId(String countryId) {
		terminalResource.countryId = UUID.fromString(countryId);
		return this;
	}

	public TerminalBuilder withLatitude(BigDecimal latitude) {
		terminalResource.latitude = latitude;
		return this;
	}

	public TerminalBuilder withLongitude(BigDecimal longitude) {
		terminalResource.longitude = longitude;
		return this;
	}

	public TerminalBuilder withUpdatedBy(String updatedBy) {
		terminalResource.updatedBy = updatedBy;
		return this;
	}

	public TerminalBuilder withUpdatedDateTime(LocalDateTime updatedDateTime) {
		terminalResource.updatedDateTime = updatedDateTime;
		return this;
	}

	public TerminalResource buildTerminal() {
		return terminalResource;
	}
}
