package com.equinor.cargotrackerreference.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinor.cargotracker.common.domain.Analysis;
import com.equinor.cargotracker.common.domain.Grade;
import com.equinor.cargotracker.common.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.AuthorityHelper;
import com.equinor.cargotrackerreference.controller.resources.GradeIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeResourceConverter;
import com.equinor.cargotrackerreference.controller.resources.GradeResourceExcel;
import com.equinor.cargotrackerreference.repository.GradeRepository;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@Service
@Transactional
public class GradeService {

	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private CountryService countryService;

	@Autowired
	private TradingAreaService tradingAreaService;
	
	
	
	public Iterable<Grade> getAllGrades() {
		return gradeRepository.findAllByOrderByName();
	}

	public Iterable<Grade> getGradesForTradingArea(UUID id) {
		return gradeRepository.findAllByTradingAreaId(id.toString());
	}
	
	public Optional<Grade> getGradeForName(String name) {
		return gradeRepository.findByName(name);
	}
	
	public Optional<Grade> getGrade(UUID id) {
		return gradeRepository.findById(id.toString());
	}

	public Grade createGrade(Grade grade) throws SQLServerException {
		if (grade.isVerified() && !AuthorityHelper.isSuperUser()) {
			throw new InvalidOperationException("Unable to create grade with status of verified. Only superusers can verify grade!");
		}
		return gradeRepository.save(grade);
	}

	public Grade updateGrade(Grade updatedGrade) {
		return gradeRepository.save(updatedGrade);
	}
	
	public void updateGrades(List<GradeResourceExcel> excelGradesFromFile) {
		gradeRepository.saveAll(getGrades(excelGradesFromFile));
	}

	public Iterable<Grade> findAllGradesForRegion(UUID id) {
		return gradeRepository.findAllByTradingAreaId(id.toString());
	}

	public void updateGradesWithDeletedTradingAreaToNull(UUID id) {
		gradeRepository.updateGradesWithDeletedTradingAreaToNull(id.toString());
	}
	

	//Check effect when removing from main project
	public void replaceGrade(UUID fromId, GradeIdNameProperty toGrade) {
		if (fromId.equals(toGrade.getId())) {
			throw new InvalidOperationException("Replacing a grade with the same grade does not make any sense"); 
		}


		//analyticsCargoService.replaceGradeOnCargoes(fromId, toGrade);
		//analyticsCargoAvailabilityService.mergeGradeForCargoAvailability(fromId.toString(), toGrade.id.toString());
		gradeRepository.deleteById(fromId.toString());
	}

	public void cancelGrade(UUID id) {
		Grade persistedGrade = gradeRepository.findById(id.toString()).orElseThrow();
		persistedGrade.setCancelled(true);
		gradeRepository.save(persistedGrade);
	}

	public Grade patchRegionId(UUID id, UUID tradingAreaId) {
		Grade persistedGrade = gradeRepository.findById(id.toString()).orElseThrow();
		persistedGrade.setTradingAreaId(tradingAreaId);
		return gradeRepository.save(persistedGrade);
	}

	public Grade verifyGrade(UUID id, boolean isVerified) {
		Grade grade = getGrade(id).orElseThrow(() -> new InvalidOperationException("The grade with id " + id + " no longer exists on the server"));
		grade.setVerified(isVerified);
		return gradeRepository.save(grade);
	}
	
	private List<Grade> getGrades(List<GradeResourceExcel> gradeResourcesExcel) {
		Map<String, UUID> tradingAreas = tradingAreaService.getTradingAreasAsMap();
		Map<String, UUID> countries = countryService.getCountriesAsMap();
		return gradeResourcesExcel.stream().map(excelGrade -> getGrade(countries, tradingAreas, excelGrade)).collect(Collectors.toList());
	}

	private Grade getGrade(Map<String, UUID> countries, Map<String, UUID> tradingAreas, GradeResourceExcel gradeResourceExcel) {
		verify(gradeResourceExcel);
		Grade grade = getGradeForName(gradeResourceExcel.name).orElse(null);
		
		UUID currentTradingAreaId = grade != null ? grade.getTradingAreaId() : null;
		UUID updatedTradingAreaId = getId(tradingAreas, currentTradingAreaId, gradeResourceExcel.tradingArea, gradeResourceExcel.reference);
		UUID updatedCountryId = countries.get(mapNameFromCOECountry(gradeResourceExcel.country));
		
		if (grade != null) {
			grade = merge(grade, gradeResourceExcel, updatedTradingAreaId, updatedCountryId);
		} else {
			grade = GradeResourceConverter.createNewGradeFromExcelResource(gradeResourceExcel, updatedTradingAreaId, updatedCountryId);
		}
		return grade;
	}

	private String mapNameFromCOECountry(String name) {
		var mapping = new HashMap<String, String>();
		mapping.put("United States of America", "United States");
		mapping.put("Saudi-Iraq Neutral Zone", "Saudi Arabia");
		mapping.put("Saudi-Kuwait Neutral Zone", "Saudi Arabia");
		mapping.put("Congo", "Congo Brazzaville"); 
		mapping.put("Trinidad and Tobago", "Trinidad Tobago");
		
		if (mapping.containsKey(name)) {
			return mapping.get(name);
		}
		return name;
	}

	private void verify(GradeResourceExcel gradeResourceExcel) {
		if (StringUtils.isEmpty(gradeResourceExcel.reference)) {
			throw new InvalidOperationException("Unable to create grade with name: " + gradeResourceExcel.name + ". Missing reference.");
		}

		if (gradeResourceExcel.validFrom == null) {
			throw new InvalidOperationException("Unable to create grade with reference: " + gradeResourceExcel.reference + ". Missing valid from date.");
		}
	}
	
	private UUID getId(Map<String, UUID> nameAndId, UUID currentId, String name, String reference) {
		UUID id = null;
		if (StringUtils.contains(name, "<Unchanged>")) {
			id = currentId;
		} else {
			if (nameAndId.containsKey(name)) {
				id = nameAndId.get(name);
			} else if (!StringUtils.isEmpty(name)) {
				throw new InvalidOperationException("/n Unable to find id for " + name + ". Unable to create/update grade " + reference);
			}
		}
		return id;
	}
	
	private Grade merge(Grade grade, GradeResourceExcel gradeResourceExcel, UUID updatedTradingAreaId, UUID updatedCountryId) {
		Analysis updatedAnalysis = getAnalysis(gradeResourceExcel, updatedCountryId);
		Analysis existingAnalysis = getExistingAnalysis(grade.getAnalyses(), updatedAnalysis).orElse(null);
		if (existingAnalysis != null) {
			existingAnalysis.setApi(updatedAnalysis.getApi());
			existingAnalysis.setSulphur(updatedAnalysis.getSulphur());
			existingAnalysis.setCountryId(updatedCountryId);
			existingAnalysis.setReference(gradeResourceExcel.reference);
		} else {
			grade.appendAnalysis(updatedAnalysis);
		}
		grade.setTradingAreaId(updatedTradingAreaId);
		
		return grade;
	}

	private Optional<Analysis> getExistingAnalysis(List<Analysis> historicAnalyses, Analysis updatedAnalysis) {
		return historicAnalyses.stream().filter(analysis -> analysis.getFromDate().equals(updatedAnalysis.getFromDate())).findFirst();
	}

	private static Analysis getAnalysis(GradeResourceExcel grade, UUID updatedCountryId) {
		Analysis analysis = new Analysis();
		analysis.setApi(grade.api);
		analysis.setSulphur(grade.sulphur);
		analysis.setFromDate(grade.validFrom);
		analysis.setReference(grade.reference);
		analysis.setCountryId(updatedCountryId);
		return analysis;
	}

}
