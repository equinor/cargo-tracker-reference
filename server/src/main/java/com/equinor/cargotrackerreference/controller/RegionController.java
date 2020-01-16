package com.equinor.cargotrackerreference.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.equinor.cargotrackerreference.domain.Region;
import com.equinor.cargotrackerreference.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.service.RegionService;

@RestController
@RequestMapping(value = "/ct/config")
@CrossOrigin(origins = "*")
public class RegionController {

	@Autowired
	private RegionService regionService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/region", method = RequestMethod.GET)
	public Iterable<Region> getAllRegions() {
		logger.debug("Getting all regions");
		return regionService.getRegions();
	}

	@RequestMapping(value = "/region/{id}", method = RequestMethod.GET)
	public Region getRegion(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting region with id {}", id);
		return regionService.getRegion(id).orElse(null);
	}

	@RequestMapping(value = "/region", method = RequestMethod.POST)
	public Region createRegion(@RequestBody Region region) {
		logger.debug("Creating region {}", region);
		try {
			return regionService.createRegion(region);
		} catch (DataIntegrityViolationException e) {
			String errormessage = "Unable to create region. Already exists a region with name " + region.getName();
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}
	}

	@RequestMapping(value = "/region/{id}", method = RequestMethod.PUT)
	public Region updateRegion(@PathVariable(value = "id") UUID id, @RequestBody Region region) {
		logger.debug("Updating region {}", region);
		return regionService.updateRegion(id, region);
	}

	@RequestMapping(value = "/region/{id}", method = RequestMethod.DELETE)
	public void deleteRegion(@PathVariable(value = "id") UUID id) {
		logger.debug("Deleting region with id {}", id);
		try {
			regionService.deleteRegion(id);
		} catch (DataIntegrityViolationException e) {
			String errormessage = "Unable to delete region with id "+ id + ". Region is in use.";
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}
	}
}
