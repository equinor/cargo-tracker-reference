package com.equinor.cargotrackerreference.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.equinor.cargotrackerreference.controller.resources.CompanyResource;

public interface CompanyResourceRepository extends Repository<CompanyResource, String>, CompanyResourceRepositoryCustom {
	
	@Query("SELECT DISTINCT c FROM CompanyResource c LEFT JOIN FETCH c.aliases a order by c.name")
	public Iterable<CompanyResource> findAll();
	
	public CompanyResource findById(String id);
}
