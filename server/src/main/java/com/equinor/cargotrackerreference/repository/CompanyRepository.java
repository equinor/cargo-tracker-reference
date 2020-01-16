package com.equinor.cargotrackerreference.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equinor.cargotrackerreference.domain.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

}
