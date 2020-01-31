package com.equinor.cargotrackerreference.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equinor.cargotrackerreference.domain.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, String> {

	public Iterable<Country> findAllByRegionId(String id); 
	
	public Iterable<Country> findAllByOrderByName(); 

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE CTREF.country SET region_id = NULL where region_id =?1", nativeQuery = true)
	public void updateCountriesWithDeletedRegionToNull(String id);
	
}
