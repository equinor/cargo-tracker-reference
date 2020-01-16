package com.equinor.cargotrackerreference.controller.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GradeResource {

	public GradeResource() {
	}

	public UUID id;
	public String name;
	public UUID tradingAreaId;
	public UUID countryId;
	public int version;
	public String ocdName;
	public String reference;
	public List<AnalysisResource> historicAnalyses = new ArrayList<>();
	public String source;
	public BigDecimal api;
	public BigDecimal sulphur;
	public LocalDate validFrom;
	public LocalDate validTo;
	public boolean verified;
	public boolean cancelled;
	public LocalDateTime updatedDateTime;
	public String updatedBy;
	
	@Override
	public String toString() {
		return "GradeResource [id=" + id + ", name=" + name + ", historicAnalyses=" + historicAnalyses + ", api=" + api
				+ ", sulphur=" + sulphur + ", validFrom=" + validFrom + ", verified=" + verified + "]";
	}
	
	
}
