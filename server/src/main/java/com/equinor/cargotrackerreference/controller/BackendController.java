package com.equinor.cargotrackerreference.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.equinor.cargotracker.common.domain.Company;
import com.equinor.cargotracker.common.domain.Country;
import com.equinor.cargotracker.common.domain.Grade;
import com.equinor.cargotracker.common.domain.Region;
import com.equinor.cargotracker.common.domain.Terminal;
import com.equinor.cargotracker.common.domain.TradingArea;
import com.equinor.cargotrackerreference.service.CompanyService;
import com.equinor.cargotrackerreference.service.CountryService;
import com.equinor.cargotrackerreference.service.GradeService;
import com.equinor.cargotrackerreference.service.RegionService;
import com.equinor.cargotrackerreference.service.TerminalService;
import com.equinor.cargotrackerreference.service.TradingAreaService;
import com.microsoft.applicationinsights.core.dependencies.google.common.collect.Iterables;

@RestController
@RequestMapping(value = "/backend")
@CrossOrigin(origins = "*")
public class BackendController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private RegionService regionService; 
	
	@Autowired
	private TradingAreaService tradingAreaService;
	
	@Autowired
	private TerminalService terminalService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	Company[] getAllCompanies() {
		logger.debug("Getting all companies");
		return Iterables.toArray(companyService.getAllCompanies(), Company.class);
	}
	
	@RequestMapping(value = "/countries", method = RequestMethod.GET)
	Country[] getAllCountries() {
		logger.debug("Getting all countries");
		return Iterables.toArray(countryService.getAllCountries(), Country.class);
	}
	
	@RequestMapping(value = "/grades", method = RequestMethod.GET)
	Grade[] getAllGrades() {
		logger.debug("Getting all grades");
		return Iterables.toArray(gradeService.getAllGrades(), Grade.class);
	}
	
	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	Region[] getAllRegions() {
		logger.debug("Getting all regions");
		return Iterables.toArray(regionService.getRegions(), Region.class);
	}
	
	@RequestMapping(value = "/tradingareas", method = RequestMethod.GET)
	TradingArea[] getAllTradingAreas() {
		logger.debug("Getting all tradingareas");
		return Iterables.toArray(tradingAreaService.getTradingAreas(), TradingArea.class);
	}

	@RequestMapping(value = "/terminals", method = RequestMethod.GET)
	Terminal[] getAllTerminals() {
		logger.debug("Getting all terminals");
		return Iterables.toArray(terminalService.getTerminals(), Terminal.class);
	}

}
