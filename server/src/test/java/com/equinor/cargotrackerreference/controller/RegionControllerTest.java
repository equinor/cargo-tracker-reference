
package com.equinor.cargotrackerreference.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.equinor.cargotrackerreference.Application;
import com.equinor.cargotrackerreference.MasterdataSetup;
import com.equinor.cargotrackerreference.builder.AnalyticsCargoResourceBuilder;
import com.equinor.cargotrackerreference.builder.GradeResourceBuilder;
import com.equinor.cargotrackerreference.builder.RegionBuilder;
import com.equinor.cargotrackerreference.builder.TradingAreaBuilder;
import com.equinor.cargotrackerreference.controller.resources.GradeResource;
import com.equinor.cargotrackerreference.controller.resources.analyticscargoresource.AnalyticsCargoResource;
import com.equinor.cargotrackerreference.domain.Region;
import com.equinor.cargotrackerreference.domain.TradingArea;
import com.equinor.cargotrackerreference.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.service.TradingAreaService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = { "h2" })
public class RegionControllerTest extends MasterdataSetup {

	@Autowired
	private RegionController regionController;
	
	@Autowired
	private TradingAreaService tradingAreaService;

	@Autowired
	private GradeController gradeController;
	
	
	@Test
	@WithUserDetails("user@equinor.com")
	public void createRegion() {
		// updatedDateTime from the client should be ignored
		LocalDateTime updatedDateTime = LocalDateTime.now().minusHours(5);
		LocalDateTime minTime = LocalDateTime.now().minusSeconds(2);
		LocalDateTime maxTime = LocalDateTime.now().plusSeconds(2);

		Region region = RegionBuilder.aRegion()
				.withName("WAF")
				.withIsActive(true)
				.withUpdatedBy("HLOLS") // updated by will be ignored
				.withUpdatedDateTime(updatedDateTime)
				.buildRegion();

		Region result = regionController.createRegion(region);
		Assert.assertEquals("WAF", result.getName());
		Assert.assertEquals(true, result.isActive());
		Assert.assertEquals("user", result.getUpdatedBy());
		
		Assert.assertTrue(result.getUpdatedDateTime().isAfter(minTime)
				&& result.getUpdatedDateTime().isBefore(maxTime));
	}

	@Test
	@WithUserDetails("user@equinor.com")
	public void createRegion_updateRegion_regionUpdated() {
		LocalDateTime updatedDateTime = LocalDateTime.now();

		Region region = RegionBuilder.aRegion()
				.withName("WAF")
				.withIsActive(true)
				.withUpdatedBy("HLOLS")
				.withUpdatedDateTime(updatedDateTime)
				.buildRegion();

		regionController.createRegion(region);
		region.setName("SYSTEM");
		Region result = regionController.updateRegion(region.getIdAsUUID(), region);
		Assert.assertEquals("SYSTEM", result.getName());

	}

	@Test
	@WithUserDetails("user@equinor.com")
	public void createRegionWithNoGrades_deleteRegion_regionDeleted() {
		LocalDateTime updatedDateTime = LocalDateTime.now();

		Region region = RegionBuilder.aRegion()
				.withName("WAF")
				.withIsActive(true)
				.withUpdatedBy("HLOLS")
				.withUpdatedDateTime(updatedDateTime)
				.buildRegion();

		regionController.createRegion(region);
		regionController.deleteRegion(region.getIdAsUUID());
		Assert.assertEquals(null, regionController.getRegion(region.getIdAsUUID()));

	}

	@Test
	@WithUserDetails("user@equinor.com")
	public void createRegionWithGrades_deleteRegion_regionDeletedAndRegionIdSetToNullOnGrade() {
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
				.withApi(new BigDecimal(12.34))
				.withSulphur(new BigDecimal(4.33))
				.withSource("OCD")
				.withTradingAreaId(tradingArea.getIdAsUUID())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		grade = gradeController.createGrade(grade);

		//Delete trading area
		tradingAreaService.deleteTradingArea(tradingArea.getIdAsUUID());		

		//Get updated grade
		GradeResource updatedGrade = gradeController.getGrade(grade.id);

		//Check if tradingAreaId is set to null after trading area is deleted
		Assert.assertEquals(null, updatedGrade.tradingAreaId);

	}
	
	@Test
	@WithUserDetails("user@equinor.com")
	public void deleteRegion_regionInUse_regionNotDeleted() {
		AnalyticsCargoResource cargoResource = AnalyticsCargoResourceBuilder.createDefault().tradingArea(wafTradingAreaResource).sourceRegion(africaRegionResource).get();
		
		exception.expect(InvalidOperationException.class);
		regionController.deleteRegion(africaRegionResource.getId());
	}
}
