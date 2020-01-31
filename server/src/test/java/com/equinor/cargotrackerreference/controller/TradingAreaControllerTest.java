
package com.equinor.cargotrackerreference.controller;

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
import com.equinor.cargotrackerreference.builder.TradingAreaBuilder;
import com.equinor.cargotrackerreference.domain.TradingArea;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = { "h2" })
public class TradingAreaControllerTest extends MasterdataSetup {

	@Autowired
	private TradingAreaController taController;
	
	@Test
	@WithUserDetails("user@equinor.com")
	public void createTradingArea() {
		
		LocalDateTime minTime = LocalDateTime.now().minusSeconds(2);
		LocalDateTime maxTime = LocalDateTime.now().plusSeconds(2);
		
		TradingArea tradingArea = 
				TradingAreaBuilder
					.aTradingArea()
					.withName("West Africa")
					.buildTradingArea();
					
		TradingArea result = taController.createTradingArea(tradingArea);
		Assert.assertEquals("West Africa", result.getName());
		Assert.assertEquals(true, result.isActive());
		Assert.assertEquals("user", result.getUpdatedBy());
		Assert.assertTrue(result.getUpdatedDateTime().isAfter(minTime)
							&& result.getUpdatedDateTime().isBefore(maxTime));

	}
}
