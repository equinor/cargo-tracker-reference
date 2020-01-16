
package com.equinor.cargotrackerreference.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.equinor.cargotrackerreference.Application;
import com.equinor.cargotrackerreference.builder.RefineryBuilder;
import com.equinor.cargotrackerreference.builder.RegionBuilder;
import com.equinor.cargotrackerreference.controller.RefineryController;
import com.equinor.cargotrackerreference.domain.Refinery;
import com.equinor.cargotrackerreference.domain.Region;
import com.equinor.cargotrackerreference.service.RegionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = { "h2" })
@WithUserDetails("user@equinor.com")
public class RefineryControllerTest {

	@Autowired
	private RefineryController refineryController;

	@Autowired
	private RegionService regionService;

	@Test
	public void createRefinery() {
		LocalDateTime updatedDateTime = LocalDateTime.now();

		// Create a region first
		Region region = RegionBuilder.aRegion().withName("WAF").withIsActive(true).withUpdatedBy("HLOLS").withUpdatedDateTime(updatedDateTime).buildRegion();
		Region result = regionService.createRegion(region);

		// Create a refinery for the region
		Refinery refinery = RefineryBuilder.aRefinery().withLocation("Europe").withOwner("Equinor").withName("Refinery1").withComments("Refinery1 in use")
				.withRegionId(result.getId()).withLatitude(new BigDecimal(33.532)).withLongitude(new BigDecimal(1.2878)).withUpdatedBy("HLOLS")
				.withUpdatedDateTime(updatedDateTime).buildRefinery();
		Refinery resultRefinery = refineryController.createRefinery(refinery);

		assertEquals("Europe", resultRefinery.getLocation());
		assertEquals("Equinor", resultRefinery.getOwner());
		assertEquals("Refinery1", resultRefinery.getName());
		assertEquals("Refinery1 in use", resultRefinery.getComments());
		assertEquals(result.getId(), resultRefinery.getRegionId());
		assertEquals(new BigDecimal(33.532).setScale(7, RoundingMode.UP), resultRefinery.getLatitude().setScale(7, RoundingMode.UP));
		assertEquals(new BigDecimal(1.2878).setScale(7, RoundingMode.UP), resultRefinery.getLongitude().setScale(7, RoundingMode.UP));
		assertEquals("user", resultRefinery.getUpdatedBy());

	}

	@Test
	public void createRefinery_updateRefinery_refineryUpdated() {
		LocalDateTime updatedDateTime = LocalDateTime.now();

		// Create a region first
		Region region = RegionBuilder.aRegion().withName("WAF").withIsActive(true).withUpdatedBy("HLOLS").withUpdatedDateTime(updatedDateTime).buildRegion();
		Region result = regionService.createRegion(region);

		// Create a refinery for the region
		Refinery refinery = RefineryBuilder.aRefinery().withLocation("Europe").withOwner("Equinor").withName("Refinery1").withComments("Refinery1 in use")
				.withRegionId(result.getId()).withLatitude(new BigDecimal(33.532)).withLongitude(new BigDecimal(1.2878)).withUpdatedBy("HLOLS")
				.withUpdatedDateTime(updatedDateTime).buildRefinery();
		refineryController.createRefinery(refinery);

		// Update location
		refinery.setName("Refinery2");
		Refinery resultRefinery = refineryController.updateRefinery(refinery.getIdAsUUID(), refinery);

		assertEquals("Refinery2", resultRefinery.getName());

	}

	@Test
	public void createRefinery_deleteRefinery_refineryDeleted() {
		LocalDateTime updatedDateTime = LocalDateTime.now();

		// Create a region first
		Region region = RegionBuilder.aRegion().withName("WAF").withIsActive(true).withUpdatedBy("HLOLS").withUpdatedDateTime(updatedDateTime).buildRegion();
		Region result = regionService.createRegion(region);

		// Create a refinery for the region
		Refinery refinery = RefineryBuilder.aRefinery().withLocation("Europe").withOwner("Equinor").withName("Refinery1").withComments("Refinery1 in use")
				.withRegionId(result.getId()).withLatitude(new BigDecimal(33.532)).withLongitude(new BigDecimal(1.2878)).withUpdatedBy("HLOLS")
				.withUpdatedDateTime(updatedDateTime).buildRefinery();
		refineryController.createRefinery(refinery);

		// Delete location
		refineryController.cancelRefinery(refinery.getIdAsUUID());

		assertTrue(refineryController.getRefinery(refinery.getIdAsUUID()).isCancelled());

	}

}
