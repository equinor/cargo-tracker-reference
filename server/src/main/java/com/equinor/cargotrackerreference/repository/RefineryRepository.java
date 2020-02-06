package com.equinor.cargotrackerreference.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equinor.cargotracker.common.domain.Refinery;


@Repository
public interface RefineryRepository extends CrudRepository<Refinery, String> {

	public Iterable<Refinery> findAllByRegionId(String id); 

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE CTREF.refinery SET region_id = NULL where region_id =?1", nativeQuery = true)
	public void updateRefineriesWithDeletedRegionToNull(String id);
}
