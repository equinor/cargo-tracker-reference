package com.equinor.cargotrackerreference.controller.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.equinor.cargotrackerreference.domain.Analysis;

public class AnalysisResource {
	
	public AnalysisResource() {
	}
	public AnalysisResource(Analysis analysis) {
		this.id = analysis.getId().toString();
		this.version = analysis.getVersion();
		this.api = analysis.getApi();
		this.countryId = analysis.getCountryId();
		this.reference = analysis.getReference();
		this.fromDate = analysis.getFromDate();
		this.toDate = analysis.getToDate();
		this.sulphur = analysis.getSulphur();
		this.updatedBy = analysis.getUpdatedBy();
		this.updatedDateTime = analysis.getUpdatedDateTime();
	}
	
	public BigDecimal api;
	public BigDecimal sulphur;
	public LocalDate fromDate;
	public LocalDate toDate;
	public UUID countryId;
	public String reference;
	
	public String id;

	public int version;
	public LocalDateTime updatedDateTime;
	public String updatedBy;

}
