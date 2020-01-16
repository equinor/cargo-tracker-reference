package com.equinor.cargotrackerreference.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equinor.cargotrackerreference.domain.Terminal;

@Repository
public interface TerminalRepository extends CrudRepository<Terminal, String> {

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE ct.terminal SET region_id = NULL where region_id =?1", nativeQuery = true)
	public void updateTerminalsWithDeletedRegionToNull(String id);
	
	public Iterable<Terminal> findAllByOrderByName();

}
