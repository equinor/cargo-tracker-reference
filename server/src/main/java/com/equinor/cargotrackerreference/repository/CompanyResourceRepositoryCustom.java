package com.equinor.cargotrackerreference.repository;

import org.springframework.data.repository.Repository;

import com.equinor.cargotrackerreference.controller.resources.CompanyResource;

public interface CompanyResourceRepositoryCustom extends Repository<CompanyResource, String> {
	
	public CompanyResource findByAlias(String Alias);
}
