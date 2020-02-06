package com.equinor.cargotrackerreference.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinor.cargotracker.common.domain.Terminal;
import com.equinor.cargotrackerreference.repository.TerminalRepository;

@Service
@Transactional
public class TerminalService {

	@Autowired
	private TerminalRepository terminalRepository;

	public Optional<Terminal> getTerminal(UUID id) {
		return terminalRepository.findById(id.toString());
	}

	public Iterable<Terminal> getTerminals() {
		return terminalRepository.findAllByOrderByName();
	}

	public Map<UUID, String> getTerminalsMap() {
		Map<UUID, String> terminalsMap = new HashMap<>();
		getTerminals().forEach(terminal -> terminalsMap.put(terminal.getIdAsUUID(), terminal.getName()));
		return terminalsMap;
	}

	public Terminal createTerminal(Terminal terminal) {
		return terminalRepository.save(terminal);
	}

	public Terminal updateTerminal(UUID id, Terminal terminal) {
		getTerminal(id).orElseThrow(() -> new IllegalStateException("The terminal with id " + terminal.getId() + " no longer exists on the server"));
		return terminalRepository.save(terminal);
	}

	public void cancelTerminal(UUID id) {
		Terminal terminal = getTerminal(id).orElseThrow(() -> new IllegalStateException("The terminal with id " + id + " no longer exists on the server"));
		terminal.setCancelled(true);
		terminalRepository.save(terminal);
	}

}
