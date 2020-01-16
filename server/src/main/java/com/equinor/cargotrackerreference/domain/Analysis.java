package com.equinor.cargotrackerreference.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.equinor.cargotrackerreference.controller.resources.AnalysisResource;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Analysis {

	private BigDecimal api;
	private BigDecimal sulphur;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String countryId;
	private String reference;
	
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private String id;

	@Version
	private int version;
	@NotNull
	@LastModifiedDate
	@CreatedDate
	private LocalDateTime updatedDateTime;
	
	@NotNull
	@LastModifiedBy
	@CreatedBy
	private String updatedBy;

	public Analysis(UUID id) {
		this.id = id != null ? id.toString() : UUID.randomUUID().toString();
	}
	
	public Analysis() {
		this.id = UUID.randomUUID().toString();
	}


	public Analysis(AnalysisResource analysisResource) {
		this.id = analysisResource.id;
		this.api = analysisResource.api;
		this.sulphur = analysisResource.sulphur;
		this.reference = analysisResource.reference;
		this.countryId = analysisResource.countryId != null ? analysisResource.countryId.toString() : null;
		this.fromDate = analysisResource.fromDate;
		this.toDate = analysisResource.toDate;
		this.version = analysisResource.version;
		this.updatedDateTime = analysisResource.updatedDateTime;
		this.updatedBy = analysisResource.updatedBy;
		
	}

	public UUID getId() {
		return UUID.fromString(id);
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public UUID getCountryId() {
		return countryId != null ?  UUID.fromString(countryId) : null;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId != null ? countryId.toString() : null;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public BigDecimal getApi() {
		return api;
	}

	public void setApi(BigDecimal api) {
		this.api = api;
	}

	public BigDecimal getSulphur() {
		return sulphur;
	}

	public void setSulphur(BigDecimal sulphur) {
		this.sulphur = sulphur;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(api, countryId, fromDate, id, reference, sulphur, toDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Analysis other = (Analysis) obj;
		return Objects.equals(api, other.api) && Objects.equals(countryId, other.countryId)
				&& Objects.equals(fromDate, other.fromDate) && Objects.equals(id, other.id)
				&& Objects.equals(reference, other.reference) && Objects.equals(sulphur, other.sulphur)
				&& Objects.equals(toDate, other.toDate);
	}

	@Override
	public String toString() {
		return "Analysis [api=" + api + ", sulphur=" + sulphur + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", reference=" + reference + ", id=" + id + "]";
	}

	
	
	
}
