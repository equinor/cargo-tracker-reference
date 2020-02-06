package com.equinor.cargotrackerreference.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinor.cargotracker.common.domain.Region;
import com.equinor.cargotrackerreference.repository.RegionRepository;

@Service
@Transactional
public class RegionService {

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private CountryService countryService;

	@Autowired
	private RefineryService refineryService;

	public Iterable<Region> getRegions() {
		return regionRepository.findAllByOrderByName();
	}

	public Map<UUID, String> getRegionsMap() {
		Map<UUID, String> regionsMap = new HashMap<>();
		getRegions().forEach(region -> regionsMap.put(region.getIdAsUUID(), region.getName()));
		return regionsMap;
	}

	public Optional<Region> getRegion(UUID id) {
		return regionRepository.findById(id.toString());
	}

	public Region createRegion(Region region) {
		return regionRepository.save(region);
	}

	public Region updateRegion(UUID id, Region region) {
		regionRepository.findById(region.getIdAsUUID().toString())
				.orElseThrow(() -> new IllegalStateException("The region with id " + region.getId() + " no longer exists on the server"));
		return regionRepository.save(region);
	}

	public void deleteRegion(UUID id) {
		String stringId = id.toString();
		refineryService.updateRefineriesWithDeletedRegionToNull(id);
		countryService.updateCountryWithDeletedRegionToNull(id);
		regionRepository.deleteById(stringId);
	}
}
