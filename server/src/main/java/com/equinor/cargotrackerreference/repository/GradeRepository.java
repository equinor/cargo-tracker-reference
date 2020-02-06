package com.equinor.cargotrackerreference.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equinor.cargotracker.common.domain.Grade;

@Repository
public interface GradeRepository extends CrudRepository<Grade, String> {

	public Iterable<Grade> findAllByTradingAreaId(String id);
	
	public Optional<Grade> findByOcdName(String name);
	
	public Optional<Grade> findByName(String name);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE CTREF.grade SET trading_area_id = NULL where trading_area_id =?1", nativeQuery = true)
	public void updateGradesWithDeletedTradingAreaToNull(String id);

}
