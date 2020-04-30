package com.equinor.cargotrackerreference.kpler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.equinor.cargotrackerreference.controller.resources.CountryIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.RegionIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TerminalIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TradingAreaIdNameProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonSerialize()
@ApiModel(value = "AnalyticsCargo", description = "Model describing an Analytics Cargo")
public class AnalyticsCargoResource {

	@ApiModelProperty(value = "Identifier of the Analytics Cargo")
	public String id;

	@ApiModelProperty(value = "The trading desk/area the cargo originated from")
	public TradingAreaIdNameProperty tradingArea;

	@ApiModelProperty(value = "Is the cargo available in the market")
	public Boolean available;

	@ApiModelProperty(value = "Is the cargo confidential")
	public Boolean confidential;

	@ApiModelProperty(value = "Origin date", required = false)
	public LocalDate dateOrigin;
	
	@ApiModelProperty(value = "Destination date", required = false)
	public LocalDate dateDestination;

	public List<TitleTransferResource> titleTransfers;

	@ApiModelProperty(value = "Comments")
	public String comments;

	@ApiModelProperty(value = "Grade")
	public GradeIdNameProperty grade;

	@ApiModelProperty(value = "Destination port")
	public TerminalIdNameProperty destinationPort;

	@ApiModelProperty(value = "Origin port")
	public TerminalIdNameProperty originPort;

	@ApiModelProperty(value = "Destination region")
	public RegionIdNameProperty destinationRegion;

	@ApiModelProperty(value = "Source country")
	public CountryIdNameProperty sourceCountry;

	@ApiModelProperty(value = "Destination country")
	public CountryIdNameProperty destinationCountry;

	@ApiModelProperty(value = "Source region")
	public RegionIdNameProperty sourceRegion;

	@ApiModelProperty(value = "Source system")
	public String sourceSystem;

	@ApiModelProperty(value = "Source system unique identifier")
	public String sourceSystemIdentifier;

	@ApiModelProperty(value = "Source system reference")
	public String sourceSystemReference;

	@ApiModelProperty(value = "Level of certainty on the properties where this is tracked")
	public Map<String, Integer> fieldStatus = new HashMap<String, Integer>();

	@ApiModelProperty(value = "Version number of the Analytics Cargo")
	public int version;

	@ApiModelProperty(value = "Vessel name")
	public String vesselName;

	@ApiModelProperty(value = "Vessel type")
	public String vesselType;

	@ApiModelProperty(value = "Trade status")
	public String tradeStatus;

	public String buyers;
	public String sellers;

	@ApiModelProperty(value = "Quantity in tons")
	public BigDecimal volume;

	@ApiModelProperty(value = "Unified system of establishing payment of freight rate")
	public String worldScale;

	@ApiModelProperty(value = "Status cancelled or not cancelled")
	public boolean cancelled;

	@ApiModelProperty(value = "Last update by")
	public String updatedBy;

	@ApiModelProperty(value = "Updated date and time")
	public LocalDateTime updatedDateTime;

	public AnalyticsCargoResource() {
	}

	@Override
	public String toString() {
		return "AnalyticsCargoResource [id=" + id + ", tradingArea=" + tradingArea + ", available=" + available
				+ ", grade=" + grade + ", destinationRegion=" + destinationRegion + ", dateOrigin=" + dateOrigin + ", dateDestination="
				+ dateDestination + ", sourceRegion=" + sourceRegion
				+ ", sourceSystemIdentifier=" + sourceSystemIdentifier + ", sourceSystemReference="
				+ sourceSystemReference + ", version=" + version + ", volume=" + volume + "]";
	}
	
}
