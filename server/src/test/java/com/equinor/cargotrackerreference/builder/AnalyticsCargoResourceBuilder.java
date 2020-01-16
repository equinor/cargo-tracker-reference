package com.equinor.cargotrackerreference.builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.equinor.cargotrackerreference.controller.resources.CountryIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.RegionIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TerminalIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TitleTransferResource;
import com.equinor.cargotrackerreference.controller.resources.TradingAreaIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.analyticscargoresource.AnalyticsCargoResource;
import com.equinor.cargotrackerreference.domain.Cargo;

public class AnalyticsCargoResourceBuilder {

	private AnalyticsCargoResource resource;

	private AnalyticsCargoResourceBuilder() {
		resource = new AnalyticsCargoResource();
	}

	public static AnalyticsCargoResourceBuilder create() {
		return new AnalyticsCargoResourceBuilder();
	}

	public static AnalyticsCargoResourceBuilder createDefault() {
		return new AnalyticsCargoResourceBuilder().available(true).blDate(LocalDate.now()).comments("This is a comment").destinationRegionStatus(3)
				.month(YearMonth.now()).sourceSystem("Cargo Tracking")
				.sourceSystemIdentifier("230123/2").sourceSystemReference("20180101").vesselName("Evita").volume(new BigDecimal(855)).worldScale("2.44")
				.tradingArea(new TradingAreaIdNameProperty(UUID.randomUUID(), ""));
	}

	public AnalyticsCargoResourceBuilder tradingArea(TradingAreaIdNameProperty tradingArea) {
		resource.tradingArea = tradingArea;
		return this;
	}

	public AnalyticsCargoResourceBuilder available(Boolean available) {
		resource.available = available;
		return this;
	}

	public AnalyticsCargoResourceBuilder confidential(Boolean confidential) {
		resource.confidential = confidential;
		return this;
	}

	public AnalyticsCargoResourceBuilder comments(String comments) {
		resource.comments = comments;
		return this;
	}

	public AnalyticsCargoResourceBuilder destinationPort(TerminalIdNameProperty destinationPort) {
		resource.destinationPort = destinationPort;
		return this;
	}

	public AnalyticsCargoResourceBuilder destinationRegion(RegionIdNameProperty destinationRegion) {
		resource.destinationRegion = destinationRegion;
		return this;
	}

	public AnalyticsCargoResourceBuilder destinationRegionStatus(Integer status) {
		resource.fieldStatus.put(Cargo.DESTINATION_REGION_STATUS, status);
		return this;
	}

	public AnalyticsCargoResourceBuilder grade(GradeIdNameProperty grade) {
		resource.grade = grade;
		return this;
	}

	public AnalyticsCargoResourceBuilder laycanEnd(LocalDate laycanEnd) {
		resource.laycanEnd = laycanEnd;
		return this;
	}

	public AnalyticsCargoResourceBuilder laycanStart(LocalDate laycanStart) {
		resource.laycanStart = laycanStart;
		return this;
	}

	public AnalyticsCargoResourceBuilder sourceCountry(CountryIdNameProperty sourceCountry) {
		resource.sourceCountry = sourceCountry;
		return this;
	}

	public AnalyticsCargoResourceBuilder volume(BigDecimal volume) {
		resource.volume = volume;
		return this;
	}

	public AnalyticsCargoResourceBuilder worldScale(String worldScale) {
		resource.worldScale = worldScale;
		return this;
	}

	public AnalyticsCargoResourceBuilder sourceSystemIdentifier(String sourceSystemIdentifier) {
		resource.sourceSystemIdentifier = sourceSystemIdentifier;
		return this;
	}

	public AnalyticsCargoResourceBuilder sourceSystemReference(String sourceSystemReference) {
		resource.sourceSystemReference = sourceSystemReference;
		return this;
	}

	public AnalyticsCargoResourceBuilder sourceSystem(String sourceSystem) {
		resource.sourceSystem = sourceSystem;
		return this;
	}

	public AnalyticsCargoResourceBuilder sourceRegion(RegionIdNameProperty sourceRegion) {
		resource.sourceRegion = sourceRegion;
		return this;
	}

	public AnalyticsCargoResourceBuilder blDate(LocalDate blDate) {
		resource.blDate = blDate;
		return this;
	}

	public AnalyticsCargoResourceBuilder vesselName(String vesselName) {
		resource.vesselName = vesselName;
		return this;
	}

	public AnalyticsCargoResourceBuilder month(YearMonth month) {
		resource.month = month;
		return this;
	}

	public AnalyticsCargoResourceBuilder appendTitleTransfer(TitleTransferResource titleTransfer) {
		if (resource.titleTransfers == null) {
			resource.titleTransfers = new ArrayList<>();
		}
		resource.titleTransfers.add(titleTransfer);
		return this;
	}

	public AnalyticsCargoResourceBuilder setTitleTransfers(List<TitleTransferResource> titleTransfers) {
		resource.titleTransfers = titleTransfers;
		return this;
	}

	public AnalyticsCargoResource get() {
		return resource;
	}
}
