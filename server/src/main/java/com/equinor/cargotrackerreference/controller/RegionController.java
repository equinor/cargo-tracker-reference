package com.equinor.cargotrackerreference.controller;

import java.util.Optional;
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

import com.equinor.cargotracker.common.domain.Region;
import com.equinor.cargotracker.common.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.config.AzureServiceBusConfiguration;
import com.equinor.cargotrackerreference.service.JmsService;
import com.equinor.cargotrackerreference.service.RegionService;

@RestController
@RequestMapping(value = "/ctref/config")
@CrossOrigin(origins = "*")
public class RegionController {

	@Autowired
	private RegionService regionService;
	
	@Autowired
	private JmsService jmsService;
	
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
			Region newRegion = regionService.createRegion(region);
			jmsService.sendJmsTopicMessage(newRegion, AzureServiceBusConfiguration.REGION_TYPE, "create");
			return newRegion;
		} catch (DataIntegrityViolationException e) {
			String errormessage = "Unable to create region. Already exists a region with name " + region.getName();
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}
	}

	@RequestMapping(value = "/region/{id}", method = RequestMethod.PUT)
	public Region updateRegion(@PathVariable(value = "id") UUID id, @RequestBody Region region) {
		logger.debug("Updating region {}", region);
		Region updatedRegion = regionService.updateRegion(id, region);
		jmsService.sendJmsTopicMessage(updatedRegion, AzureServiceBusConfiguration.REGION_TYPE, "update");
		return updatedRegion;
	}

	@RequestMapping(value = "/region/{id}", method = RequestMethod.DELETE)
	public void deleteRegion(@PathVariable(value = "id") UUID id) {
		logger.debug("Deleting region with id {}", id);
		try {
			Optional<Region> regionToBeDeleted = regionService.getRegion(id);
			regionService.deleteRegion(id);
			jmsService.sendJmsTopicMessage(regionToBeDeleted, AzureServiceBusConfiguration.REGION_TYPE, "delete");
		} catch (DataIntegrityViolationException e) {
			String errormessage = "Unable to delete region with id "+ id + ". Region is in use.";
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}
	}
}
