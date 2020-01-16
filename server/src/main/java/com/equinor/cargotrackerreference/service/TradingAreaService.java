package com.equinor.cargotrackerreference.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinor.cargotrackerreference.domain.TradingArea;
import com.equinor.cargotrackerreference.repository.TradingAreaRepository;

@Service
@Transactional
public class TradingAreaService {

	@Autowired
	private TradingAreaRepository tradingAreaRepository;

	@Autowired
	private GradeService gradeService;
	
	public Iterable<TradingArea> getTradingAreas() {
		return tradingAreaRepository.findAll();
	}
	
	public Map<String, UUID> getTradingAreasAsMap() {
		Map<String, UUID> tradingAreas = new HashMap<>();
		getTradingAreas().forEach(tradingArea -> tradingAreas.put(tradingArea.getName(), tradingArea.getIdAsUUID()));
		return tradingAreas;
	}

	public Optional<TradingArea> getTradingArea(UUID id) {
		return tradingAreaRepository.findById(id.toString());
	}

	public TradingArea createTradingArea(TradingArea tradingArea) {		
		return tradingAreaRepository.save(tradingArea);
	}

	public TradingArea updateTradingArea(UUID id, TradingArea tradingArea) {
		tradingAreaRepository.findById(tradingArea.getIdAsUUID().toString())
				.orElseThrow(() -> new IllegalStateException("The trading area with id " + tradingArea.getId() + " no longer exists on the server"));
		return tradingAreaRepository.save(tradingArea);
	}

	public void deleteTradingArea(UUID id) {
		gradeService.updateGradesWithDeletedTradingAreaToNull(id);		
		tradingAreaRepository.deleteById(id.toString());
	}
}
