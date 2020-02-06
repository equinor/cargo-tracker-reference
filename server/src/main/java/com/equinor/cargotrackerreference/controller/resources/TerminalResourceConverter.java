package com.equinor.cargotrackerreference.controller.resources;

import com.equinor.cargotracker.common.domain.Terminal;

public class TerminalResourceConverter {
	public static TerminalResource createResourceFromTerminal(Terminal terminal) {
		if (terminal == null) {
			return null;
		}
		TerminalResource terminalResource = new TerminalResource();
		terminalResource.id = terminal.getIdAsUUID();
		terminalResource.latitude = terminal.getLatitude();
		terminalResource.longitude = terminal.getLongitude();
		terminalResource.name = terminal.getName();
		terminalResource.source = terminal.getSource();
		terminalResource.countryId = terminal.getCountryId();
		terminalResource.cancelled = terminal.isCancelled();
		terminalResource.aliases = terminal.getAliases();
		terminalResource.version = terminal.getVersion();
		terminalResource.updatedBy = terminal.getUpdatedBy();
		terminalResource.updatedDateTime = terminal.getUpdatedDateTime();
		return terminalResource;
	}

	public static Terminal createTerminalFromResource(TerminalResource terminalResource) {
		if (terminalResource == null) {
			return null;
		}
		Terminal terminal = new Terminal(terminalResource.id);
		terminal.setLatitude(terminalResource.latitude);
		terminal.setLongitude(terminalResource.longitude);
		terminal.setName(terminalResource.name);
		terminal.setSource(terminalResource.source);
		terminal.setCountryId(terminalResource.countryId);
		terminal.setCancelled(terminalResource.cancelled);
		if (terminalResource.aliases != null) {
			terminal.setAliases(terminalResource.aliases);
		}
		terminal.setVersion(terminalResource.version);
		
		return terminal;
	}
}
