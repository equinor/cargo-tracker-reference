package com.equinor.cargotrackerreference.controller.resources.analyticscargoresource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PostLoad;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.equinor.cargotrackerreference.controller.resources.CountryIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.RegionIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TerminalIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TitleTransferResource;
import com.equinor.cargotrackerreference.controller.resources.TradingAreaIdNameProperty;
import com.equinor.cargotrackerreference.utils.YearMonthConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "ANALYTICS_CARGO_RM")
@Immutable
@JsonSerialize()
@ApiModel(value = "AnalyticsCargo", description = "Model describing an Analytics Cargo")
public class AnalyticsCargoResource {
	@PostLoad
	void init() {
		if (grade != null) {
			grade.init(month);
		}
	}

	@ApiModelProperty(value = "Identifier of the Analytics Cargo")
	@Id
	public String id;

	@ApiModelProperty(value = "The trading desk/area the cargo originated from")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tradingAreaId")
	public TradingAreaIdNameProperty tradingArea;

	@ApiModelProperty(value = "Is the cargo available in the market")
	public Boolean available;

	@ApiModelProperty(value = "Is the cargo confidential")
	public Boolean confidential;

	@ApiModelProperty(value = "B/L date", required = false)
	public LocalDate blDate;

	@ApiModelProperty(value = "Reporting month", required = true)
	@Convert(converter = YearMonthConverter.class)
	public YearMonth month;

	@OneToMany(targetEntity = TitleTransferResource.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "cargo_id")
	@OrderColumn(name = "sequence")
	public List<TitleTransferResource> titleTransfers;

	@ApiModelProperty(value = "Comments")
	public String comments;

	@ApiModelProperty(value = "Grade")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gradeId")
	public GradeIdNameProperty grade;

	@ApiModelProperty(value = "Destination port")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destinationPortId")
	public TerminalIdNameProperty destinationPort;

	@ApiModelProperty(value = "Destination region")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destinationRegionId")
	public RegionIdNameProperty destinationRegion;

	@ApiModelProperty(value = "Laycan end")
	public LocalDate laycanEnd;

	@ApiModelProperty(value = "Laycan start")
	public LocalDate laycanStart;

	@ApiModelProperty(value = "Source country")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sourceCountryId")
	public CountryIdNameProperty sourceCountry;

	@ApiModelProperty(value = "Source region")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sourceRegionId")
	public RegionIdNameProperty sourceRegion;

	@ApiModelProperty(value = "Source system")
	public String sourceSystem;

	@ApiModelProperty(value = "Source system unique identifier")
	public String sourceSystemIdentifier;

	@ApiModelProperty(value = "Source system reference")
	public String sourceSystemReference;

	@ApiModelProperty(value = "Level of certainty on the properties where this is tracked")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "analytics_cargo_property_status_rm", joinColumns = @JoinColumn(name = "cargo_id"))
	@MapKeyColumn(name = "name")
	@Column(name = "status")
	public Map<String, Integer> fieldStatus = new HashMap<String, Integer>();

	@ApiModelProperty(value = "Version number of the Analytics Cargo")
	public int version;

	@ApiModelProperty(value = "Vessel name")
	@Column(name = "vessel")
	public String vesselName;

	@ApiModelProperty(value = "Volume in kbbls")
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
				+ ", month=" + month + ", grade=" + grade + ", destinationRegion=" + destinationRegion + ", laycanEnd="
				+ laycanEnd + ", laycanStart=" + laycanStart + ", sourceRegion=" + sourceRegion
				+ ", sourceSystemIdentifier=" + sourceSystemIdentifier + ", sourceSystemReference="
				+ sourceSystemReference + ", version=" + version + ", volume=" + volume + "]";
	}
	
	
}
