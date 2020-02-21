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

import com.equinor.cargotracker.common.domain.TradingArea;
import com.equinor.cargotracker.common.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.controller.resources.GradeResource;
import com.equinor.cargotrackerreference.controller.resources.GradeResourceIterator;
import com.equinor.cargotrackerreference.service.GradeService;
import com.equinor.cargotrackerreference.service.JmsService;
import com.equinor.cargotrackerreference.service.TradingAreaService;

@RestController
@RequestMapping(value = "/ctref/config")
@CrossOrigin(origins = "*")
public class TradingAreaController {
	
	@Autowired
	private TradingAreaService tradingAreaService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private JmsService jmsService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/tradingarea", method = RequestMethod.GET)
	public Iterable<TradingArea> getAllTradingAreas() {
		logger.debug("Getting all trading areas");
		return tradingAreaService.getTradingAreas();
	}
	
	@RequestMapping(value = "/tradingarea/{id}/grade", method = RequestMethod.GET)
	public Iterable<GradeResource> getGradesForTradingAreas(@PathVariable(value = "id") UUID id) {
		return new GradeResourceIterator(gradeService.getGradesForTradingArea(id).iterator());
	}

	@RequestMapping(value = "/tradingarea/{id}", method = RequestMethod.GET)
	public TradingArea getTradingArea(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting trading area with id ", id);
		return tradingAreaService.getTradingArea(id).orElse(null);
	}

	@RequestMapping(value = "/tradingarea", method = RequestMethod.POST)
	public TradingArea createTradingArea(@RequestBody TradingArea tradingArea) {
		logger.debug("Creating trading area ", tradingArea);
		try {
			// TODO: Quickfix, improve.
			TradingArea newTradingArea = new TradingArea();
			newTradingArea.setActive(tradingArea.isActive());
			newTradingArea.setName(tradingArea.getName());			
			jmsService.sendJmsMessage(newTradingArea, "tradingarea", "create");
			return tradingAreaService.createTradingArea(newTradingArea);
		} catch (DataIntegrityViolationException e) {
			String errormessage = "Unable to create trading area. Already exists a trading area with name " + tradingArea.getName();
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}
	}

	@RequestMapping(value = "/tradingarea/{id}", method = RequestMethod.PUT)
	public TradingArea updateTradingArea(@PathVariable(value = "id") UUID id, @RequestBody TradingArea tradingArea) {
		logger.debug("Updating trading area ", tradingArea);
		TradingArea updatedTradingArea = tradingAreaService.updateTradingArea(id, tradingArea);
		jmsService.sendJmsMessage(updatedTradingArea, "tradingarea", "update");
		return updatedTradingArea;
	}

	@RequestMapping(value = "/tradingarea/{id}", method = RequestMethod.DELETE)
	public void deleteTradingArea(@PathVariable(value = "id") UUID id) {
		logger.debug("Deleting trading area with id ", id);
		try {
			Optional<TradingArea> tradingAreToBeDeleted = tradingAreaService.getTradingArea(id);
			tradingAreaService.deleteTradingArea(id);
			jmsService.sendJmsMessage(tradingAreToBeDeleted, "tradingarea", "delete");
		} catch (DataIntegrityViolationException e) {
			String errormessage = "Unable to delete trading area with id " + id + ". Trading area is in use.";
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}
	}
}