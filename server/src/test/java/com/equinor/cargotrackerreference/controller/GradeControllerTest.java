
package com.equinor.cargotrackerreference.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.equinor.cargotrackerreference.Application;
import com.equinor.cargotrackerreference.MasterdataSetup;
import com.equinor.cargotrackerreference.builder.GradeResourceBuilder;
import com.equinor.cargotrackerreference.builder.TradingAreaBuilder;
import com.equinor.cargotrackerreference.controller.resources.AnalysisResource;
import com.equinor.cargotrackerreference.controller.resources.GradeIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeResource;
import com.equinor.cargotrackerreference.controller.resources.analyticscargoresource.IdNameProperty;
import com.equinor.cargotrackerreference.domain.TradingArea;
import com.equinor.cargotrackerreference.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.service.TradingAreaService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = { "h2" })
@WithUserDetails("user@equinor.com")
public class GradeControllerTest extends MasterdataSetup {

	@Autowired
	private GradeController gradeController;
	
	@Autowired
	private TradingAreaService tradingAreaService;
	
	@Test
	public void createGrade() {
		LocalDateTime updatedDateTime = LocalDateTime.now();
		
		TradingArea newTradingArea = TradingAreaBuilder.aTradingArea()
				.withName("West Africa")
				.withIsActive(true)
				.withUpdatedBy("NIAND")
				.withUpdatedDateTime(updatedDateTime)
				.buildTradingArea();
		
		TradingArea tradingArea = tradingAreaService.createTradingArea(newTradingArea);

		//Create a grade for the region
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("DALIA")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(tradingArea.getIdAsUUID())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		
		GradeResource resultGrade = gradeController.createGrade(grade);

		assertEquals("DALIA", resultGrade.name);
		assertEquals(new BigDecimal(20.56).setScale(7, RoundingMode.UP), resultGrade.api.setScale(7, RoundingMode.UP));
		assertEquals(new BigDecimal(3.44).setScale(7, RoundingMode.UP), resultGrade.sulphur.setScale(7, RoundingMode.UP));
		assertEquals("OCD", resultGrade.source);
		assertEquals(tradingArea.getIdAsUUID(), resultGrade.tradingAreaId);		
	}

	@Test
	public void createGrade_updateGrade_gradeUpdated() {
		TradingArea newTradingArea = TradingAreaBuilder.aTradingArea()
				.withName("West Africa")
				.withIsActive(true)
				.buildTradingArea();
		
		TradingArea tradingArea = tradingAreaService.createTradingArea(newTradingArea);

		//Create a grade for the region
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("DALIA")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(tradingArea.getIdAsUUID())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		grade = gradeController.createGrade(grade);

		//Update grade
		grade.name = "SYSTEM";
		GradeResource resultGrade = gradeController.updateGrade(grade.id, grade);

		assertEquals("SYSTEM", resultGrade.name);

	}

	@Test
	public void createGrade_deleteGrade_gradeDeleted() {
		TradingArea newTradingArea = TradingAreaBuilder.aTradingArea()
				.withName("West Africa")
				.withIsActive(true)
				.buildTradingArea();
		
		TradingArea tradingArea = tradingAreaService.createTradingArea(newTradingArea);

		//Create a grade for the region
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("DALIA")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(tradingArea.getIdAsUUID())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		grade = gradeController.createGrade(grade);

		//delete grade
		gradeController.cancelGrade(grade.id);

		assertTrue(gradeController.getGrade(grade.id).cancelled);

	}
	

	@Test
	@Ignore
	public void createGrade_patchTradingAreaId_gradeUpdated() {
		LocalDateTime updatedDateTime = LocalDateTime.now();

		TradingArea newWAFArea = TradingAreaBuilder.aTradingArea()
				.withName("West Africa")
				.withIsActive(true)
				.withUpdatedBy("NIAND")
				.withUpdatedDateTime(updatedDateTime)
				.buildTradingArea();
		
		TradingArea wafArea = tradingAreaService.createTradingArea(newWAFArea);

		TradingArea newNWEArea = TradingAreaBuilder.aTradingArea()
				.withName("North Sea")
				.withIsActive(true)
				.withUpdatedBy("NIAND")
				.withUpdatedDateTime(updatedDateTime)
				.buildTradingArea();
		
		TradingArea nweArea = tradingAreaService.createTradingArea(newNWEArea);

		//Create a grade for the WAF region
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("DALIA")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(wafArea.getIdAsUUID())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		grade = gradeController.createGrade(grade);

		//Grade object with only regionId set as sent from client
		GradeResource patchGrade = new GradeResource();
		patchGrade.tradingAreaId = nweArea.getIdAsUUID();
		gradeController.patchRegionId(grade.id, patchGrade);

		assertEquals(nweArea.getIdAsUUID(), gradeController.getGrade(grade.id).tradingAreaId);

	}
	
	@Test
	public void uploadGrades_duplicateGradesAreUploaded_gradesAreCreated() {
		final List<String> gradeRefList = Arrays.asList(
				"Balder");
		try {
			doUpload("gradeUpload3.xlsx");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		var gradeMap = getGradeMap(gradeRefList);
		
		assertEquals(1, gradeMap.size());
		GradeResource gradeBalder = gradeMap.get("Balder");
		assertNotNull(gradeBalder);
		assertEquals("Balder", gradeBalder.name);
		assertThat(new BigDecimal("26.42"),  Matchers.comparesEqualTo(gradeBalder.api));
		assertThat(new BigDecimal("0.64"),  Matchers.comparesEqualTo(gradeBalder.sulphur));
		assertEquals(LocalDate.of(2016, 6, 1), gradeBalder.validFrom);
		assertTrue(StringUtils.isEmpty(gradeBalder.ocdName));
		assertNull(gradeBalder.tradingAreaId);
		assertEquals(uganda.id, gradeBalder.countryId.toString());
		
		AnalysisResource latestAnalysis = gradeBalder.historicAnalyses.get(0);
		assertThat(new BigDecimal("26.42"),  Matchers.comparesEqualTo(latestAnalysis.api));
		assertThat(new BigDecimal("0.64"),  Matchers.comparesEqualTo(latestAnalysis.sulphur));
		assertNull(latestAnalysis.toDate);
		assertEquals(LocalDate.of(2016, 6, 1), latestAnalysis.fromDate);
		assertEquals("BALDER201601", latestAnalysis.reference);
		assertEquals(uganda.id, latestAnalysis.countryId.toString());
		
		AnalysisResource firstAnalysis = gradeBalder.historicAnalyses.get(1);
		assertThat(new BigDecimal("31.89"),  Matchers.comparesEqualTo(firstAnalysis.api));
		assertThat(new BigDecimal("0.46"),  Matchers.comparesEqualTo(firstAnalysis.sulphur));
		assertEquals(LocalDate.of(2016, 5, 31), firstAnalysis.toDate);
		assertEquals(LocalDate.of(2000, 1, 1), firstAnalysis.fromDate);
		assertEquals("BALDER200901", firstAnalysis.reference);
		assertEquals(sudan.id, firstAnalysis.countryId.toString());
		
	}
	
	@Test
	public void uploadGrades_gradesAreUploaded_gradesAreCreatedAndUpdated() {
		final List<String> gradeRefList = Arrays.asList(
				"Aasta Hansteen", 
				"Abo", 
				"Acacia", 
				"Alaskan North Slope", 
				"Access Western Blend", 
				"Agbami");
		
		// First upload with 3 grades
		try {
			doUpload("gradeUpload.xlsx");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		var gradeMap = getGradeMap(gradeRefList);
		
		assertEquals(3, gradeMap.size());

		GradeResource gradeAasta = gradeMap.get("Aasta Hansteen");
		assertNotNull(gradeAasta);
		assertEquals("Aasta Hansteen", gradeAasta.name);
		assertThat(new BigDecimal("40.09"),  Matchers.comparesEqualTo(gradeAasta.api));
		assertThat(new BigDecimal("1.07"),  Matchers.comparesEqualTo(gradeAasta.sulphur));
		assertEquals(LocalDate.of(2019, 1, 1), gradeAasta.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAasta.ocdName));
		assertEquals(sudan.id, gradeAasta.countryId.toString());
		
		GradeResource gradeAbo = gradeMap.get("Abo");
		assertNotNull(gradeAbo);
		assertEquals("Abo", gradeAbo.name);
		assertThat(new BigDecimal("39.60"),  Matchers.comparesEqualTo(gradeAbo.api));
		assertThat(new BigDecimal("1.97"),  Matchers.comparesEqualTo(gradeAbo.sulphur));
		assertEquals(LocalDate.of(2019, 2, 1), gradeAbo.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAbo.ocdName));
		assertEquals(uganda.id, gradeAbo.countryId.toString());
		
		GradeResource gradeAca = gradeMap.get("Acacia");
		assertNotNull(gradeAca);
		assertEquals("Acacia", gradeAca.name);
		assertThat(new BigDecimal("41.98"),  Matchers.comparesEqualTo(gradeAca.api));
		assertThat(new BigDecimal("1.98"),  Matchers.comparesEqualTo(gradeAca.sulphur));
		assertEquals(LocalDate.of(2019, 1, 1), gradeAca.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAca.ocdName));
		assertEquals(trinidadTobago.id, gradeAca.countryId.toString());
		
		// Second upload with 6 grades. 3 updates and 3 new grades
		try {
			doUpload("gradeUpload2.xlsx");
		} catch (Exception e) {
			throw new RuntimeException("Upload failed!");
		}
		
		gradeMap = getGradeMap(gradeRefList);
		assertEquals(6, gradeMap.size());
		
		gradeAasta = gradeMap.get("Aasta Hansteen");
		assertNotNull(gradeAasta);
		assertEquals("Aasta Hansteen", gradeAasta.name);
		assertThat(new BigDecimal("41.09"),  Matchers.comparesEqualTo(gradeAasta.api));
		assertThat(new BigDecimal("1.08"),  Matchers.comparesEqualTo(gradeAasta.sulphur));
		assertEquals(2, gradeAasta.historicAnalyses.size());
		AnalysisResource oldAnalysisAasta = gradeAasta.historicAnalyses.get(1);
		assertThat(new BigDecimal("40.09"),  Matchers.comparesEqualTo(oldAnalysisAasta.api));
		assertThat(new BigDecimal("1.07"),  Matchers.comparesEqualTo(oldAnalysisAasta.sulphur));
		assertEquals(LocalDate.of(2019, 2, 28), oldAnalysisAasta.toDate);
		assertEquals(LocalDate.of(2019, 1, 1), oldAnalysisAasta.fromDate);
		AnalysisResource updatedAnalysisAasta = gradeAasta.historicAnalyses.get(0);
		assertThat(new BigDecimal("41.09"),  Matchers.comparesEqualTo(updatedAnalysisAasta.api));
		assertThat(new BigDecimal("1.08"),  Matchers.comparesEqualTo(updatedAnalysisAasta.sulphur));
		assertNull(updatedAnalysisAasta.toDate);
		assertEquals(LocalDate.of(2019, 3, 1), updatedAnalysisAasta.fromDate);
		assertEquals(LocalDate.of(2019, 3, 1), gradeAasta.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAasta.ocdName));
		assertEquals(sudan.id, gradeAasta.countryId.toString());
		
		gradeAbo = gradeMap.get("Abo");
		assertNotNull(gradeAbo);
		assertEquals("Abo", gradeAbo.name);
		assertThat(new BigDecimal("29.60"),  Matchers.comparesEqualTo(gradeAbo.api));
		assertThat(new BigDecimal("1.97"),  Matchers.comparesEqualTo(gradeAbo.sulphur));
		assertEquals(1, gradeAbo.historicAnalyses.size());
		assertEquals(LocalDate.of(2019, 2, 1), gradeAbo.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAbo.ocdName));
		assertNull(gradeAbo.countryId);
		
		gradeAca = gradeMap.get("Acacia");
		assertNotNull(gradeAca);
		assertEquals("Acacia", gradeAca.name);
		assertThat(new BigDecimal("41.98"),  Matchers.comparesEqualTo(gradeAca.api));
		assertThat(new BigDecimal("1.98"),  Matchers.comparesEqualTo(gradeAca.sulphur));
		assertEquals(2, gradeAca.historicAnalyses.size());
		AnalysisResource oldAnalysisAca = gradeAca.historicAnalyses.get(1);
		assertThat(new BigDecimal("31.98"),  Matchers.comparesEqualTo(oldAnalysisAca.api));
		assertThat(new BigDecimal("1.98"),  Matchers.comparesEqualTo(oldAnalysisAca.sulphur));
		assertEquals(LocalDate.of(2018, 1, 1),  oldAnalysisAca.fromDate);
		assertEquals(LocalDate.of(2018, 12, 31),  oldAnalysisAca.toDate);
		AnalysisResource latestAnalysisAca = gradeAca.historicAnalyses.get(0);
		assertThat(new BigDecimal("41.98"),  Matchers.comparesEqualTo(latestAnalysisAca.api));
		assertThat(new BigDecimal("1.98"),  Matchers.comparesEqualTo(latestAnalysisAca.sulphur));
		assertEquals(LocalDate.of(2019, 1, 1),  latestAnalysisAca.fromDate);
		assertNull(latestAnalysisAca.toDate);
		assertEquals(LocalDate.of(2019, 1, 1), gradeAca.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAasta.ocdName));
		assertEquals(trinidadTobago.id, gradeAca.countryId.toString());
		
		GradeResource gradeAlaskan = gradeMap.get("Alaskan North Slope");
		assertNotNull(gradeAlaskan);
		assertEquals("Alaskan North Slope", gradeAlaskan.name);
		assertThat(new BigDecimal("32.08"),  Matchers.comparesEqualTo(gradeAlaskan.api));
		assertThat(new BigDecimal("0.93"),  Matchers.comparesEqualTo(gradeAlaskan.sulphur));
		assertEquals(LocalDate.of(2019, 1, 1), gradeAlaskan.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAlaskan.ocdName));
		assertNull(gradeAlaskan.countryId);
		
		GradeResource gradeAccess = gradeMap.get("Access Western Blend");
		assertNotNull(gradeAccess);
		assertEquals("Access Western Blend", gradeAccess.name);
		assertThat(new BigDecimal("21.70"),  Matchers.comparesEqualTo(gradeAccess.api));
		assertThat(new BigDecimal("3.94"),  Matchers.comparesEqualTo(gradeAccess.sulphur));
		assertEquals(LocalDate.of(2019, 2, 1), gradeAccess.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAccess.ocdName));
		assertNull(gradeAccess.countryId);
		
		GradeResource gradeAgbami = gradeMap.get("Agbami");
		assertNotNull(gradeAgbami);
		assertEquals("Agbami", gradeAgbami.name);
		assertThat(new BigDecimal("47.90"),  Matchers.comparesEqualTo(gradeAgbami.api));
		assertThat(new BigDecimal("0.04"),  Matchers.comparesEqualTo(gradeAgbami.sulphur));
		assertEquals(LocalDate.of(2019, 1, 1), gradeAgbami.validFrom);
		assertTrue(StringUtils.isEmpty(gradeAgbami.ocdName));
		assertNull(gradeAgbami.countryId);
		
	}

	private HashMap<String, GradeResource> getGradeMap(final List<String> gradeRefList) {
		var gradeMap = new HashMap<String, GradeResource>();
		Iterable<GradeResource> grades = gradeController.getGrades();
		for (GradeResource gradeResource : grades) {
			if (gradeRefList.contains(gradeResource.name)) {
				gradeMap.put(gradeResource.name, gradeResource);
			}
		}
		return gradeMap;
	}

	private ResponseEntity<?> doUpload(String fileName) throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream("258581_payload.json");		
//		byte[] fileByteArray = IOUtils.toByteArray(getClass().getResourceAsStream(fileName));
		byte[] fileByteArray = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream(fileName));
		
		MockMultipartFile mockMultipartFile = new MockMultipartFile("data", fileName, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", fileByteArray);
		MockMultipartHttpServletRequest mockMultipartHttpServletRequest = new MockMultipartHttpServletRequest();
		mockMultipartHttpServletRequest.setContent(createFileContent(fileByteArray, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", fileName));
		mockMultipartHttpServletRequest.setContentType("multipart/form-data; boundary=ThisIsABoudaryDefinition");
		mockMultipartHttpServletRequest.addFile(mockMultipartFile);
	

		return gradeController.uploadFile(mockMultipartHttpServletRequest);

	}
	
	
	private byte[] createFileContent(byte[] data, String contentType, String fileName) {
		String start = "--ThisIsABoudaryDefinition \r\n Content-Disposition: form-data; name=\"file\"; filename=\""
				+ fileName + "\"\r\n Content-type: " + contentType + "\r\n\r\n";

		String end = "--ThisIsABoudaryDefinition--";
		return ArrayUtils.addAll(start.getBytes(), ArrayUtils.addAll(data, end.getBytes()));
	}
		
	@Test
	public void createGrades_verifyGrade_gradeIsVerified() {
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("TRBLEND")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(wafTradingAreaResource.getId())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		
		GradeResource persistedGrade = gradeController.createGrade(grade);
		assertFalse(persistedGrade.verified);
		
		persistedGrade.verified = true;
		persistedGrade = gradeController.verifyGrade(persistedGrade.id, persistedGrade);
		assertTrue(persistedGrade.verified);
	}
	
	@Test
	public void createGrade_gradeIsVerified_createGradeFails() {
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("TRBLEND")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(wafTradingAreaResource.getId())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.withVerified(true)
				.buildGrade();
		
		exception.expect(InvalidOperationException.class);
		gradeController.createGrade(grade);
	}

}
