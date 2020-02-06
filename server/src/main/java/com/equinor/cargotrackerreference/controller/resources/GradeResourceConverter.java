package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;
import java.util.stream.Collectors;

import com.equinor.cargotracker.common.domain.Analysis;
import com.equinor.cargotracker.common.domain.Grade;
import com.equinor.cargotracker.common.domain.SourceSystem;
import com.equinor.cargotracker.common.exceptions.InvalidOperationException;

public class GradeResourceConverter {
	public static GradeResource createResourceFromGrade(Grade grade) {
		if (grade == null) {
			return null;
		}
		GradeResource gradeResource = new GradeResource();
		gradeResource.id = grade.getIdAsUUID();
		gradeResource.name = grade.getName();
		gradeResource.version = grade.getVersion();
		gradeResource.ocdName = grade.getOcdName();
		gradeResource.source = grade.getSource();
		gradeResource.tradingAreaId = grade.getTradingAreaId();
		gradeResource.historicAnalyses = grade.getAnalyses().stream().map(analysis -> new AnalysisResource(analysis)).collect(Collectors.toList());
		gradeResource.verified = grade.isVerified();
		gradeResource.cancelled = grade.isCancelled();
		gradeResource.updatedBy = grade.getUpdatedBy();
		gradeResource.updatedDateTime = grade.getUpdatedDateTime();
		Analysis latestAnalysis = grade.getLatestAnalysis();
		if (latestAnalysis != null) {
			gradeResource.api = latestAnalysis.getApi();
			gradeResource.sulphur = latestAnalysis.getSulphur();
			gradeResource.validFrom = latestAnalysis.getFromDate();
			gradeResource.validTo = latestAnalysis.getToDate();
			gradeResource.reference = latestAnalysis.getReference();
			gradeResource.countryId = latestAnalysis.getCountryId();
		}
		return gradeResource;
	}
	
	public static Grade createNewGradeFromExcelResource(GradeResourceExcel gradeResourceExcel, UUID tradingAreaId, UUID countryId) {
		if (gradeResourceExcel == null) {
			return null;
		}
		if (gradeResourceExcel.validFrom == null) {
			throw new InvalidOperationException("Unable to create/update grade without valid from date");
		}
		Grade grade = new Grade();
		grade.setName(gradeResourceExcel.name);
		grade.setSource(SourceSystem.COE.toString());
		grade.setTradingAreaId(tradingAreaId);
		grade.setVerified(true);
		grade.setCancelled(false);
		Analysis analysis = new Analysis();
		analysis.setApi(gradeResourceExcel.api);
		analysis.setSulphur(gradeResourceExcel.sulphur);
		analysis.setFromDate(gradeResourceExcel.validFrom);
		analysis.setCountryId(countryId);
		analysis.setReference(gradeResourceExcel.reference);
		grade.appendAnalysis(analysis);

		return grade;
	}
	
	public static Grade createGradeFromResource(GradeResource gradeResource) {
		return createGradeFromResource(gradeResource, null);
	}

	public static Grade createGradeFromResource(GradeResource gradeResource, Grade existingGrade) {
		if (gradeResource == null) {
			return null;
		}
		if (gradeResource.validFrom == null) {
			throw new InvalidOperationException("Unable to create/update grade without valid from date");
		}
		Grade grade = existingGrade != null ? existingGrade : new Grade();
		grade.setName(gradeResource.name);
		grade.setVersion(gradeResource.version);
		grade.setOcdName(gradeResource.ocdName);
		grade.setSource(gradeResource.source);
		grade.setTradingAreaId(gradeResource.tradingAreaId);
		grade.setVerified(gradeResource.verified);
		grade.setCancelled(gradeResource.cancelled);
		
		Analysis analysis = grade.getLatestAnalysis() != null ? grade.getLatestAnalysis() : new Analysis();
		analysis.setApi(gradeResource.api);
		analysis.setSulphur(gradeResource.sulphur);
		analysis.setFromDate(gradeResource.validFrom);
		analysis.setCountryId(gradeResource.countryId);
		analysis.setReference(gradeResource.reference);

		if (grade.getAnalyses().isEmpty()) {
			grade.appendAnalysis(analysis);
		}
		return grade;
	}
}
