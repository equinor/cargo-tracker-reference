package com.equinor.cargotrackerreference.controller;

import java.util.Optional;
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

import com.equinor.cargotracker.common.domain.Refinery;
import com.equinor.cargotrackerreference.config.AzureServiceBusConfiguration;
import com.equinor.cargotrackerreference.service.JmsService;
import com.equinor.cargotrackerreference.service.RefineryService;

@RestController
@RequestMapping(value = "/ctref/config")
@CrossOrigin(origins = "*")
public class RefineryController {

	@Autowired
	private RefineryService refineryService;
	
	@Autowired
	private JmsService jmsService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/refinery", method = RequestMethod.GET)
	public Iterable<Refinery> getRefineries() {
		logger.debug("Getting refineries.");
		return refineryService.getRefineries();
	}

	@RequestMapping(value = "/refinery/{id}", method = RequestMethod.GET)
	public Refinery getRefinery(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting refinery with id {} ", id);
		return refineryService.getRefinery(id).orElse(null);
	}

	@RequestMapping(value = "/refinery", method = RequestMethod.POST)
	public Refinery createRefinery(@RequestBody Refinery refinery) {
		logger.debug("Creating refinery {} ", refinery);
		Refinery newRefinery = refineryService.createRefiney(refinery);
		jmsService.sendJmsTopicMessage(newRefinery, AzureServiceBusConfiguration.REFINERY_TYPE, "create");
		return newRefinery;
	}

	@RequestMapping(value = "/refinery/{id}", method = RequestMethod.PUT)
	public Refinery updateRefinery(@PathVariable(value = "id") UUID id, @RequestBody Refinery refinery) {
		logger.debug("Updating refinery {} ", refinery);
		Refinery updatedRefinery = refineryService.updateRefinery(id, refinery);
		jmsService.sendJmsTopicMessage(updatedRefinery, AzureServiceBusConfiguration.REFINERY_TYPE, "update");
		return updatedRefinery;
	}

	@RequestMapping(value = "/refinery/{id}", method = RequestMethod.DELETE)
	public void cancelRefinery(@PathVariable(value = "id") UUID id) {
		logger.debug("Cancelling refinery with id {} ", id);		
		refineryService.cancelRefinery(id);
		Optional<Refinery> cancelledRefinery = refineryService.getRefinery(id); 
		jmsService.sendJmsTopicMessage(cancelledRefinery, AzureServiceBusConfiguration.REFINERY_TYPE, "cancel");
	}

	@RequestMapping(value = "/region/{id}/refinery", method = RequestMethod.GET)
	public Iterable<Refinery> getRefineriesForRegion(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting refineries for region id {} ", id);
		return refineryService.getRefineriesForRegion(id);
	}

}
