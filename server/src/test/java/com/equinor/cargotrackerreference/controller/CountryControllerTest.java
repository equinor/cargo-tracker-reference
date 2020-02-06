
package com.equinor.cargotrackerreference.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.equinor.cargotracker.common.domain.Region;
import com.equinor.cargotrackerreference.Application;
import com.equinor.cargotrackerreference.builder.CountryBuilder;
import com.equinor.cargotrackerreference.builder.RegionBuilder;
import com.equinor.cargotrackerreference.controller.resources.CountryResource;
import com.equinor.cargotrackerreference.service.RegionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = { "h2" })
@WithUserDetails("user@equinor.com")
public class CountryControllerTest {

	@Autowired
	private CountryController countryController;

	@Autowired
	private RegionService regionService;

	@Test
	public void createCountry() {
		// Create a region first
		Region region = RegionBuilder.aRegion().withName("WAF").withIsActive(true).withUpdatedBy("HLOLS").buildRegion();
		Region result = regionService.createRegion(region);

		// Create a country for the region
		CountryResource country = CountryBuilder.aCountry().withName("Sweeden").withRegionId(result.getId()).withLatitude(new BigDecimal(12.34))
				.withLongitude(new BigDecimal(3.88)).withUpdatedBy("HLOLS").buildCountry();
		CountryResource persistedCountry = countryController.createCountry(country);

		Assert.assertEquals("Sweeden", persistedCountry.name);
		Assert.assertEquals(result.getId(), persistedCountry.regionId);
		Assert.assertEquals(new BigDecimal(12.34).setScale(7, RoundingMode.UP), persistedCountry.latitude.setScale(7, RoundingMode.UP));
		Assert.assertEquals(new BigDecimal(3.88).setScale(7, RoundingMode.UP), persistedCountry.longitude.setScale(7, RoundingMode.UP));
		Assert.assertEquals("user", persistedCountry.updatedBy);

	}

	@Test
	public void createCountry_patchRegionId_countryUpdated() {
		// Create a region WAF
		Region wafRegion = regionService.createRegion(RegionBuilder.aRegion().withName("WAF").withIsActive(true).buildRegion());

		// Create a region NWE
		Region persistedNweRegion = regionService.createRegion(RegionBuilder.aRegion().withName("NWE").withIsActive(true).buildRegion());

		// Create a country
		CountryResource location = CountryBuilder.aCountry().withName("Denmark").withRegionId(wafRegion.getId()).withLatitude(new BigDecimal(12.34))
				.withLongitude(new BigDecimal(3.88)).buildCountry();
		location = countryController.createCountry(location);
		
		// Update region to NWE
		location.regionId = persistedNweRegion.getId();
		countryController.patchRegionId(location.id, location);
		

		Assert.assertEquals(persistedNweRegion.getId(), countryController.getCountry(location.id).regionId);

		// Update region to null
		location.regionId = null;
		countryController.patchRegionId(location.id, location);
		
		Assert.assertNull(countryController.getCountry(location.id).regionId);
	}

}
