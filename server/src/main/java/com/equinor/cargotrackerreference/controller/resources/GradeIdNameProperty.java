package com.equinor.cargotrackerreference.controller.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.equinor.cargotracker.common.domain.Analysis;
import com.google.common.collect.Iterables;


public class GradeIdNameProperty extends IdNameProperty {

	public BigDecimal api;
	public BigDecimal sulphur;
	
	public boolean verified;
	
	private List<Analysis> analyses = new ArrayList<>();

	public GradeIdNameProperty(UUID id, String name) {
		super(id, name);
	}
	
	public GradeIdNameProperty(UUID id, String name, BigDecimal api, BigDecimal sulphur) {
		this(id, name);
		this.api = api;
		this.sulphur = sulphur;
	}

	public GradeIdNameProperty() {
	}

	public void init(YearMonth month) {
		Analysis analysis = getAnalysis(month.atDay(1));
		if (analysis != null) {
			this.api = analysis.getApi();
			this.sulphur = analysis.getSulphur();
		}
	}
	
	private Analysis getAnalysis(LocalDate date) {
		Analysis analysis = null;
		if (date == null) {
			analysis = getLatestAnalysis();
		} else {
			for (Analysis an : analyses) {
				if (date.isEqual(an.getFromDate()) || date.isAfter(an.getFromDate())) {
					analysis = an;
				} else {
					break;
				}
			}
		}
		return analysis;
	}
	
	private Analysis getLatestAnalysis() {
		return Iterables.getFirst(analyses, null);
	}

	@Override
	public String toString() {
		return super.toString() + " GradeIdNameProperty [api=" + api + ", sulphur=" + sulphur + ", verified=" + verified + "]";
	}

}
