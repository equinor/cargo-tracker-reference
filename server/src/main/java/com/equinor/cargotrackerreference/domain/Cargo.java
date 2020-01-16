package com.equinor.cargotrackerreference.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Cargo {
	public static final String DESTINATION_REGION_STATUS = "destinationRegion";

	private CargoProperty<Laycan> laycan;
	private CargoProperty<Country> sourceCountry;
	private CargoProperty<Grade> grade;
	private CargoProperty<BigDecimal> volume;
	private CargoProperty<String> worldScale;
	private List<CargoProperty<TitleTransfer>> titleTransfers = new ArrayList<CargoProperty<TitleTransfer>>();
	private CargoProperty<String> comments;
	private CargoProperty<Terminal> destinationPort;
	private CargoProperty<Boolean> available;
	private CargoProperty<String> vesselName;
	private CargoProperty<TradingArea> tradingArea;
	private CargoProperty<Region> sourceRegion;
	private CargoProperty<Region> destinationRegion;
	private Map<String, Integer> propertiesStatus = new HashMap<>();
	private CargoProperty<String> sourceSystemReference;
	private CargoProperty<LocalDate> blDate;
	private CargoProperty<YearMonth> month;
	private CargoProperty<Boolean> confidential;

	public Cargo() {

	}

	public Map<String, Integer> getPropertiesStatus() {
		return propertiesStatus;
	}

	public void setPropertiesStatus(Map<String, Integer> propertiesStatus) {
		this.propertiesStatus = propertiesStatus;
	}

	public Integer getStatusForProperty(String property) {
		return propertiesStatus.get(property);
	}

	public CargoProperty<Laycan> getLaycan() {
		return laycan;
	}

	@JsonIgnore
	public Laycan getLaycanValue() {
		return getCargoTrackingPropertyValue(laycan);
	}

	public void setLaycan(Laycan laycan, SourceSystem sourceSystem) {
		this.laycan = updatePropertyValue(this.laycan, laycan, sourceSystem);
	}

	public CargoProperty<Country> getSourceCountry() {
		return sourceCountry;
	}

	@JsonIgnore
	public Country getSourceCountryValue() {
		return getCargoTrackingPropertyValue(sourceCountry);
	}

	public void setSourceCountry(Country sourceCountry, SourceSystem sourceSystem) {
		this.sourceCountry = updatePropertyValue(this.sourceCountry, sourceCountry, sourceSystem);
	}

	public List<CargoProperty<TitleTransfer>> getTitleTransfers() {
		return titleTransfers;
	}

	public void setTitleTransfers(List<CargoProperty<TitleTransfer>> titleTransfers) {
		this.titleTransfers = titleTransfers;
	}

	public void setTitleTransfers(List<TitleTransfer> titleTransfers, SourceSystem sourceSystem) {
		List<CargoProperty<TitleTransfer>> updatedTitleTransfers = new ArrayList<>();
		if (titleTransfers != null) {
			int idx = 0;
			for (TitleTransfer titleTransfer : titleTransfers) {
				CargoProperty<TitleTransfer> existingTitleTransfer = getTitleTransfer(idx).orElse(null);
				if (existingTitleTransfer == null) {
					CargoProperty<TitleTransfer> targetOwner = new CargoProperty<TitleTransfer>(sourceSystem, titleTransfer);
					updatedTitleTransfers.add(targetOwner);
				} else {
					existingTitleTransfer = updatePropertyValue(existingTitleTransfer, titleTransfer, sourceSystem);
					updatedTitleTransfers.add(existingTitleTransfer);
				}
				idx++;
			}
		}
		setTitleTransfers(updatedTitleTransfers);
	}

	public Optional<CargoProperty<TitleTransfer>> getTitleTransfer(int titleTransferNumber) {
		if (titleTransfers.size() > titleTransferNumber) {
			return Optional.ofNullable(titleTransfers.get(titleTransferNumber));
		}
		return Optional.empty();
	}

	public CargoProperty<Grade> getGrade() {
		return grade;
	}

	@JsonIgnore
	public Grade getGradeValue() {
		return getCargoTrackingPropertyValue(grade);
	}

	public void setGrade(Grade grade, SourceSystem sourceSystem) {
		this.grade = updatePropertyValue(this.grade, grade, sourceSystem);
	}

	public CargoProperty<BigDecimal> getVolume() {
		return volume;
	}

	@JsonIgnore
	public BigDecimal getVolumeValue() {
		return getCargoTrackingPropertyValue(volume);
	}

	public void setVolume(BigDecimal volume, SourceSystem sourceSystem) {
		this.volume = updatePropertyValue(this.volume, volume, sourceSystem);
	}

	public CargoProperty<String> getComments() {
		return comments;
	}

	@JsonIgnore
	public String getCommentsValue() {
		return getCargoTrackingPropertyValue(comments);
	}

	public void setComments(String comments, SourceSystem sourceSystem) {
		this.comments = updatePropertyValue(this.comments, comments, sourceSystem);
	}

	public CargoProperty<Terminal> getDestinationPort() {
		return destinationPort;
	}

	@JsonIgnore
	public Terminal getDestinationPortValue() {
		return getCargoTrackingPropertyValue(destinationPort);
	}

	public void setDestinationPort(Terminal destinationPort, SourceSystem sourceSystem) {
		this.destinationPort = updatePropertyValue(this.destinationPort, destinationPort, sourceSystem);
	}

	public CargoProperty<Boolean> getAvailable() {
		return available;
	}

	@JsonIgnore
	public Boolean getAvailableValue() {
		return getCargoTrackingPropertyValue(available);
	}

	public void setAvailable(Boolean available, SourceSystem sourceSystem) {
		this.available = updatePropertyValue(this.available, available, sourceSystem);
	}

	public CargoProperty<Boolean> getConfidential() {
		return confidential;
	}

	@JsonIgnore
	public Boolean getConfidentialValue() {
		return getCargoTrackingPropertyValue(confidential);
	}

	public void setConfidential(Boolean confidential, SourceSystem sourceSystem) {
		this.confidential = updatePropertyValue(this.confidential, confidential, sourceSystem);
	}

	public CargoProperty<String> getVesselName() {
		return vesselName;
	}

	@JsonIgnore
	public String getVesselNameValue() {
		return getCargoTrackingPropertyValue(vesselName);
	}

	public void setVesselName(String vesselName, SourceSystem sourceSystem) {
		this.vesselName = updatePropertyValue(this.vesselName, vesselName, sourceSystem);
	}

	public CargoProperty<TradingArea> getTradingArea() {
		return tradingArea;
	}

	@JsonIgnore
	public TradingArea getTradingAreaValue() {
		return getCargoTrackingPropertyValue(tradingArea);
	}

	public void setTradingArea(TradingArea tradingArea, SourceSystem sourceSystem) {
		this.tradingArea = updatePropertyValue(this.tradingArea, tradingArea, sourceSystem);
	}

	public CargoProperty<Region> getSourceRegion() {
		return sourceRegion;
	}

	@JsonIgnore
	public Region getSourceRegionValue() {
		return getCargoTrackingPropertyValue(sourceRegion);
	}

	public void setSourceRegion(Region sourceRegion, SourceSystem sourceSystem) {
		this.sourceRegion = updatePropertyValue(this.sourceRegion, sourceRegion, sourceSystem);
	}

	public CargoProperty<Region> getDestinationRegion() {
		return destinationRegion;
	}

	@JsonIgnore
	public Region getDestinationRegionValue() {
		return getCargoTrackingPropertyValue(destinationRegion);
	}

	public void setDestinationRegion(Region destinationRegion, SourceSystem sourceSystem, Integer status) {
		this.destinationRegion = updatePropertyValue(this.destinationRegion, destinationRegion, sourceSystem);
		propertiesStatus.put(DESTINATION_REGION_STATUS, status);
	}

	public CargoProperty<String> getSourceSystemReference() {
		return sourceSystemReference;
	}

	@JsonIgnore
	public String getSourceSystemReferenceValue() {
		return getCargoTrackingPropertyValue(sourceSystemReference);
	}

	public void setSourceSystemReference(String sourceSystemReference, SourceSystem sourceSystem) {
		this.sourceSystemReference = updatePropertyValue(this.sourceSystemReference, sourceSystemReference, sourceSystem);
	}

	public CargoProperty<LocalDate> getBlDate() {
		return blDate;
	}

	@JsonIgnore
	public LocalDate getBlDateValue() {
		return getCargoTrackingPropertyValue(blDate);
	}

	public void setBlDate(LocalDate blDate, SourceSystem sourceSystem) {
		this.blDate = updatePropertyValue(this.blDate, blDate, sourceSystem);
	}

	public CargoProperty<YearMonth> getMonth() {
		return month;
	}

	@JsonIgnore
	public YearMonth getMonthValue() {
		return getCargoTrackingPropertyValue(month);
	}

	public void setMonth(YearMonth month, SourceSystem sourceSystem) {
		this.month = updatePropertyValue(this.month, month, sourceSystem);
	}

	public CargoProperty<String> getWorldScale() {
		return worldScale;
	}

	@JsonIgnore
	public String getWorldScaleValue() {
		return getCargoTrackingPropertyValue(worldScale);
	}

	public void setWorldScale(String worldScale, SourceSystem sourceSystem) {
		this.worldScale = updatePropertyValue(this.worldScale, worldScale, sourceSystem);
	}

	private <T> CargoProperty<T> updatePropertyValue(CargoProperty<T> property, T value, SourceSystem sourceSystem) {
		if (property != null) {
			property.updateProperty(sourceSystem, value);
		} else {
			property = new CargoProperty<T>(sourceSystem, value);
		}
		return property;
	}

	private <T> T getPropertyValue(CargoProperty<T> property, SourceSystem sourceSystem) {
		if (property == null) {
			return null;
		}
		return property.getPropertyValue(sourceSystem);
	}

	private <T> T getCargoTrackingPropertyValue(CargoProperty<T> property) {
		if (property == null) {
			return null;
		}
		return property.getCargoTrackingPropertyValue();
	}

	@Override
	public int hashCode() {
		return Objects.hash(available, blDate, comments, confidential, destinationPort, destinationRegion, grade,
				laycan, month, propertiesStatus, sourceCountry, sourceRegion, sourceSystemReference, titleTransfers,
				tradingArea, vesselName, volume, worldScale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cargo other = (Cargo) obj;
		return Objects.equals(available, other.available) && Objects.equals(blDate, other.blDate)
				&& Objects.equals(comments, other.comments) && Objects.equals(confidential, other.confidential)
				&& Objects.equals(destinationPort, other.destinationPort)
				&& Objects.equals(destinationRegion, other.destinationRegion) && Objects.equals(grade, other.grade)
				&& Objects.equals(laycan, other.laycan) && Objects.equals(month, other.month)
				&& Objects.equals(propertiesStatus, other.propertiesStatus)
				&& Objects.equals(sourceCountry, other.sourceCountry)
				&& Objects.equals(sourceRegion, other.sourceRegion)
				&& Objects.equals(sourceSystemReference, other.sourceSystemReference)
				&& Objects.equals(titleTransfers, other.titleTransfers)
				&& Objects.equals(tradingArea, other.tradingArea) && Objects.equals(vesselName, other.vesselName)
				&& Objects.equals(volume, other.volume) && Objects.equals(worldScale, other.worldScale);
	}

	@Override
	public String toString() {
		return "Cargo [laycan=" + laycan + ", sourceCountry=" + sourceCountry + ", grade=" + grade + ", volume=" + volume
				+ ", comments=" + comments + ", destinationPort=" + destinationPort + ", available=" + available + ", confidential=" + confidential + ", vesselName=" + vesselName
				+ ", sourceRegion=" + sourceRegion + ", destinationRegion=" + destinationRegion + ", propertiesStatus=" + propertiesStatus + ", sourceSystemReference="
				+ sourceSystemReference + "]";
	}
}
