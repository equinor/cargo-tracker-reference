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

import com.equinor.cargotrackerreference.controller.resources.TerminalResource;
import com.equinor.cargotrackerreference.controller.resources.TerminalResourceConverter;
import com.equinor.cargotrackerreference.controller.resources.TerminalResourceIterator;
import com.equinor.cargotrackerreference.service.TerminalService;

@RestController
@RequestMapping(value = "/ct/config")
@CrossOrigin(origins = "*")
public class TerminalController {

	@Autowired
	private TerminalService terminalService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/terminal", method = RequestMethod.GET)
	public Iterable<TerminalResource> getAllTerminals() {
		logger.debug("Getting all terminals");
		return new TerminalResourceIterator(terminalService.getTerminals().iterator());
	}

	@RequestMapping(value = "/terminal/{id}", method = RequestMethod.GET)
	public TerminalResource getTerminal(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting all terminal with id {}", id);
		return TerminalResourceConverter.createResourceFromTerminal(terminalService.getTerminal(id).orElse(null));
	}

	@RequestMapping(value = "/terminal", method = RequestMethod.POST)
	public TerminalResource createTerminal(@RequestBody TerminalResource terminal) {
		logger.debug("Creating terminal {}", terminal);
		return TerminalResourceConverter.createResourceFromTerminal(terminalService.createTerminal(TerminalResourceConverter.createTerminalFromResource(terminal)));
	}

	@RequestMapping(value = "/terminal/{id}", method = RequestMethod.PUT)
	public TerminalResource updateTerminal(@PathVariable(value = "id") UUID id, @RequestBody TerminalResource terminal) {
		logger.debug("Updating terminal {}", terminal);
		return TerminalResourceConverter.createResourceFromTerminal(terminalService.updateTerminal(id, TerminalResourceConverter.createTerminalFromResource(terminal)));
	}

	@RequestMapping(value = "/terminal/{id}", method = RequestMethod.DELETE)
	public void cancelTerminal(@PathVariable(value = "id") UUID id) {
		terminalService.cancelTerminal(id);
	}
}
