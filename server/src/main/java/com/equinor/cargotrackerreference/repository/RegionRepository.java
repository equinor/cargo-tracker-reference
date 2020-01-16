package com.equinor.cargotrackerreference.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equinor.cargotrackerreference.domain.Region;

@Repository
public interface RegionRepository extends CrudRepository<Region, String> {
	
	public Iterable<Region> findAllByOrderByName();

}
