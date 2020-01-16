package com.equinor.cargotrackerreference.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.equinor.cargotrackerreference.controller.resources.CountryResource;
import com.equinor.cargotrackerreference.controller.resources.CountryResourceConverter;
import com.equinor.cargotrackerreference.controller.resources.CountryResourceIterator;
import com.equinor.cargotrackerreference.service.CountryService;

@RestController
@RequestMapping(value = "/ct/config")
@CrossOrigin(origins = "*")
public class CountryController {

	@Autowired
	private CountryService countryService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/country", method = RequestMethod.GET)
	public Iterable<CountryResource> getAllCountries() {
		logger.debug("Getting all countries");
		return new CountryResourceIterator(countryService.getAllCountries().iterator());
	}

	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET)
	public CountryResource getCountry(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting country with id {}", id);
		return CountryResourceConverter.createResourceFromCountry(countryService.getCountry(id).orElse(null));
	}

	@RequestMapping(value = "/country/{id}", method = RequestMethod.PATCH)
	public CountryResource patchRegionId(@PathVariable(value = "id") UUID countryId, @RequestBody CountryResource country) {
		logger.debug("Setting region id for country with id: {}. New region id: ", countryId, country != null ? country.regionId : null);
		return CountryResourceConverter.createResourceFromCountry(countryService.patchRegionIdForCountry(countryId, country));
	}

	@RequestMapping(value = "/region/{id}/country", method = RequestMethod.GET)
	public Iterable<CountryResource> getCountriesForRegion(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting countries for region id {}", id);
		return new CountryResourceIterator(countryService.getCountriesForRegion(id).iterator());
	}

	public CountryResource createCountry(@RequestBody CountryResource country) {
		logger.debug("Creating country {}", country);
		return CountryResourceConverter.createResourceFromCountry(countryService.createCountry(country));
	}

}
