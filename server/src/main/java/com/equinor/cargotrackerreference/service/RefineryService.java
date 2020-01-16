package com.equinor.cargotrackerreference.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinor.cargotrackerreference.domain.Refinery;
import com.equinor.cargotrackerreference.repository.RefineryRepository;

@Service
@Transactional
public class RefineryService {

	@Autowired
	private RefineryRepository refineryRepository;

	public Iterable<Refinery> getRefineries() {
		return refineryRepository.findAll();
	}

	public Optional<Refinery> getRefinery(UUID id) {
		return refineryRepository.findById(id.toString());
	}

	public Refinery createRefiney(Refinery refinery) {
		return refineryRepository.save(refinery);
	}

	public Refinery updateRefinery(UUID id, Refinery refinery) {
		refineryRepository.findById(id.toString()).orElseThrow(() -> new IllegalStateException("The refinery with id " + refinery.getId() + " no longer exists on the server"));
		return refineryRepository.save(refinery);
	}

	public void cancelRefinery(UUID id) {
		Refinery refinery = refineryRepository.findById(id.toString()).orElseThrow(() -> new IllegalStateException("The refinery with id " + id + " no longer exists on the server"));
		refinery.setCancelled(true);
		refineryRepository.save(refinery);
	}

	public Iterable<Refinery> getRefineriesForRegion(UUID id) {
		return refineryRepository.findAllByRegionId(id.toString());
	}
	
	public void updateRefineriesWithDeletedRegionToNull(UUID id) {
		refineryRepository.updateRefineriesWithDeletedRegionToNull(id.toString());
	}

}
