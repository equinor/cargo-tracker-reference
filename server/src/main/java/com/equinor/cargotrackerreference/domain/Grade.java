package com.equinor.cargotrackerreference.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "grade")
public class Grade extends VersionedEntityName {

	private String ocdName;
	
	@OneToMany(targetEntity=Analysis.class, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "grade_id")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("fromDate DESC")
	private List<Analysis> analyses = new ArrayList<>();
	
	private String source;
	private boolean verified;
	
	@Column(name = "trading_area_id")
	private String tradingAreaId;
	
	private boolean cancelled = false;
	
	public Grade() {
	}
	
	public Grade(UUID id) {
		super(id);
	}

	public List<Analysis> getAnalyses() {
		return analyses;
	}

	public void setAnalyses(List<Analysis> analyses) {
		this.analyses = analyses;
	}

	public void setTradingAreaId(String tradingAreaId) {
		this.tradingAreaId = tradingAreaId;
	}

	public String getOcdName() {
		return ocdName;
	}

	public void setOcdName(String ocdName) {
		this.ocdName = ocdName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public UUID getTradingAreaId() {
		return tradingAreaId != null ? UUID.fromString(tradingAreaId) : null;
	}

	public void setTradingAreaId(UUID tradingAreaId) {
		this.tradingAreaId = tradingAreaId != null ? tradingAreaId.toString() : null;
	}
	
	public void appendAnalysis(Analysis analysis) {
		Analysis previousAnalysis = getAnalysisAt(analysis.getFromDate());	
		if (previousAnalysis != null) {
			previousAnalysis.setToDate(analysis.getFromDate().minusDays(1));
		}
		Analysis analysisAfter = getAnalysisAfter(analysis.getFromDate());
		if (analysisAfter != null) {
			analysis.setToDate(analysisAfter.getFromDate().minusDays(1));
		}
		this.analyses.add(analysis);
	}
	public boolean isVerified() {
		return verified;
	}
	
	public void appendAnalyses(List<Analysis> analyses) {
		analyses.forEach(analysis -> this.appendAnalysis(analysis));
	}

	@JsonIgnore
	public Analysis getLatestAnalysis() {
		Analysis latestAnalysis = null;
		for (Analysis analysis : getAnalyses()) {
			if (latestAnalysis == null || analysis.getFromDate().isAfter(latestAnalysis.getFromDate())) {
				latestAnalysis = analysis;
			}
		}
		return latestAnalysis;
	}
	
	@JsonIgnore
	public BigDecimal getApi(LocalDate date) {
		Analysis analysis = getAnalysisAt(date);
		return analysis != null ? analysis.getApi() : null;
	}
	
	@JsonIgnore
	public BigDecimal getSulphur(LocalDate date) {
		Analysis analysis = getAnalysisAt(date);
		return analysis != null ? analysis.getSulphur() : null;
	}
	
	private Analysis getAnalysisAt(LocalDate date) {
		Analysis validAnalysisForDate = null;
		if (date == null) {
			validAnalysisForDate =  getLatestAnalysis();
		} else {
			for (Analysis analysis : getAnalyses()) {
				if ((date.isEqual(analysis.getFromDate()) || date.isAfter(analysis.getFromDate())) 
						&& (analysis.getToDate() == null || date.isEqual(analysis.getToDate()) || date.isBefore(analysis.getFromDate()))) {
					validAnalysisForDate = analysis;
				} 
			}
		}
		return validAnalysisForDate;
	}
	
	private Analysis getAnalysisAfter(LocalDate date) {
		Analysis validAnalysisAfterDate = null;
		for (Analysis analysis : getAnalyses()) {
			if (analysis.getFromDate().isAfter(date)) {
				if (validAnalysisAfterDate == null || validAnalysisAfterDate.getFromDate().isAfter(analysis.getFromDate())) {
					validAnalysisAfterDate = analysis;
				} 
			} 
		}
		return validAnalysisAfterDate;
	}
	
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(analyses, ocdName, source, tradingAreaId, verified);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grade other = (Grade) obj;
		return Objects.equals(analyses, other.analyses) && Objects.equals(ocdName, other.ocdName)
				&& Objects.equals(source, other.source) && Objects.equals(tradingAreaId, other.tradingAreaId)
				&& verified == other.verified;
	}

	@Override
	public String toString() {
		return "Grade [ocdName=" + ocdName + ", source=" + source + ", tradingAreaId="
				+ tradingAreaId + "]";
	}


	
	
}
