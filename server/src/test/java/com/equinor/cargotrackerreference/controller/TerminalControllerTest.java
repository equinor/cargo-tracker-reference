
package com.equinor.cargotrackerreference.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.junit.Ignore;
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
import com.equinor.cargotrackerreference.builder.CountryBuilder;
import com.equinor.cargotrackerreference.builder.TerminalBuilder;
import com.equinor.cargotrackerreference.controller.resources.CountryResource;
import com.equinor.cargotrackerreference.controller.resources.LocationResource;
import com.equinor.cargotrackerreference.controller.resources.TerminalResource;
import com.equinor.cargotrackerreference.controller.resources.analyticscargoresource.AnalyticsCargoResource;
import com.equinor.cargotrackerreference.exceptions.InvalidOperationException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = { "h2" })
@WithUserDetails("user@equinor.com")
public class TerminalControllerTest extends MasterdataSetup {

	@Autowired
	private TerminalController terminalController;

	@Autowired
	private CountryController countryController;
		
	@Test
	public void createTerminal() {
		// Create a country first
		CountryResource norway = countryController.createCountry(CountryBuilder.aCountry().withName("Norway").buildCountry());

		// Create a terminal
		TerminalResource terminal = TerminalBuilder.aTerminal().withName("Terminal1").withSource("OCD").withCountryId(norway.id.toString()).withLatitude(new BigDecimal(12.34))
				.withLongitude(new BigDecimal(3.88)).withUpdatedBy("HLOLS").buildTerminal();
		TerminalResource resultTerminal = terminalController.createTerminal(terminal);

		assertEquals("Terminal1", resultTerminal.name);
		assertEquals("OCD", resultTerminal.source);
		assertEquals(norway.id, resultTerminal.countryId);
		assertEquals(new BigDecimal(12.34).setScale(7, RoundingMode.UP), resultTerminal.latitude.setScale(7, RoundingMode.UP));
		assertEquals(new BigDecimal(3.88).setScale(7, RoundingMode.UP), resultTerminal.longitude.setScale(7, RoundingMode.UP));
		assertEquals("user", resultTerminal.updatedBy);

	}

	@Test
	public void createTerminal_updateTerminal_terminalUpdated() {
		LocalDateTime updatedDateTime = LocalDateTime.now();

		// Create a country first
		CountryResource norway = countryController.createCountry(CountryBuilder.aCountry().withName("Norway").buildCountry());

		// Create a terminal for the country
		TerminalResource terminalResource = TerminalBuilder.aTerminal().withName("NOR").withSource("OCD").withCountryId(norway.id.toString()).withLatitude(new BigDecimal(12.34))
				.withLongitude(new BigDecimal(3.88)).withUpdatedBy("HLOLS").withUpdatedDateTime(updatedDateTime).buildTerminal();
		terminalResource = terminalController.createTerminal(terminalResource);

		// Update country
		terminalResource.name = "SWE";
		LocationResource resultLocation = terminalController.updateTerminal(terminalResource.id, terminalResource);

		assertEquals("SWE", resultLocation.name);

	}

	@Test
	public void createTerminal_deleteTerminal_terminalDeleted() {
		LocalDateTime updatedDateTime = LocalDateTime.now();

		// Create a country first
		CountryResource norway = countryController.createCountry(CountryBuilder.aCountry().withName("Norway").buildCountry());

		// Create a terminal for the country
		TerminalResource terminalResource = TerminalBuilder.aTerminal().withName("NOR").withSource("OCD").withCountryId(norway.id.toString()).withLatitude(new BigDecimal(12.34))
				.withLongitude(new BigDecimal(3.88)).withUpdatedBy("HLOLS").withUpdatedDateTime(updatedDateTime).buildTerminal();
		terminalResource = terminalController.createTerminal(terminalResource);

		// Delete location
		terminalController.cancelTerminal(terminalResource.id);

		assertTrue(terminalController.getTerminal(terminalResource.id).cancelled);

	}
	
	@Test
	@WithUserDetails("user@equinor.com")
	@Ignore("Not valid anymore as terminal is not deleted, status is changed to cancelled.")
	public void deleteTerminal_terminalInUse_terminalNotDeleted() {
		AnalyticsCargoResource cargoResource = AnalyticsCargoResourceBuilder.createDefault().tradingArea(wafTradingAreaResource).destinationPort(terminalWaf1).get();		
		
		exception.expect(InvalidOperationException.class);
		terminalController.cancelTerminal(terminalWaf1.getId());
	}

}
