package com.equinor.cargotrackerreference.builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.equinor.cargotrackerreference.controller.resources.GradeResource;

public class GradeResourceBuilder {

	GradeResource gradeResource;
	

	private GradeResourceBuilder() {
		this.gradeResource = new GradeResource();
	}

	public static GradeResourceBuilder aGrade() {
		GradeResourceBuilder gradeBuilder = new GradeResourceBuilder();
		return gradeBuilder;
	}

	public GradeResourceBuilder withValidfrom(LocalDate date) {
		this.gradeResource.validFrom = date;
		return this;
	}
	
	public GradeResourceBuilder withName(String name) {
		this.gradeResource.name = name;
		return this;
	}

	public GradeResourceBuilder withApi(BigDecimal api) {
		this.gradeResource.api = api;
		return this;
	}

	public GradeResourceBuilder withSulphur(BigDecimal sulphur) {
		this.gradeResource.sulphur = sulphur;
		return this;
	}

	public GradeResourceBuilder withReference(String reference) {
		this.gradeResource.reference = reference;
		return this;
	}

	public GradeResourceBuilder withOcdName(String ocdName) {
		this.gradeResource.ocdName = ocdName;
		return this;
	}

	public GradeResourceBuilder withSource(String source) {
		this.gradeResource.source = source;
		return this;
	}

	public GradeResourceBuilder withTradingAreaId(UUID tradingAreaId) {
		this.gradeResource.tradingAreaId = tradingAreaId;
		return this;
	}
	
	public GradeResourceBuilder withVerified(boolean verified) {
		this.gradeResource.verified = verified;
		return this;
	}

	public GradeResource buildGrade() {
		return gradeResource;
	}
}
