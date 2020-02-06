package com.equinor.cargotrackerreference.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinor.cargotracker.common.domain.Company;
import com.equinor.cargotracker.common.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.AuthorityHelper;
import com.equinor.cargotrackerreference.controller.resources.CompanyResource;
import com.equinor.cargotrackerreference.repository.CompanyRepository;
import com.equinor.cargotrackerreference.repository.CompanyResourceRepository;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyResourceRepository companyResourceRepository;
	
	public CompanyResource findCompanyByAlias(String companyAlias) {
		return companyResourceRepository.findByAlias(companyAlias);
	}
	
	public Iterable<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Optional<Company> getCompany(UUID id) {
		return companyRepository.findById(id.toString());
	}

	public Company createCompany(Company company) throws SQLServerException {
		if (company.isVerified() && !AuthorityHelper.isSuperUser()) {
			throw new InvalidOperationException("Unable to create company with status of verified. Only superusers can verify company!");
		}
		return companyRepository.save(company);
	}

	public Company updateCompany(Company company) {
		companyRepository.findById(company.getIdAsUUID().toString()).orElseThrow(() -> new IllegalStateException("The company with id " + company.getIdAsUUID() + " no longer exists on the server"));
		return companyRepository.save(company);
	}

	public void cancelCompany(UUID id) {
		Company company = companyRepository.findById(id.toString()).orElseThrow(() -> new IllegalStateException("The company with id " + id + " no longer exists on the server"));
		company.setCancelled(true);
		companyRepository.save(company);
	}

	public Company verifyCompany(UUID id, boolean isVerified) {
		Company persistedCompany = companyRepository.findById(id.toString()).orElseThrow(() -> new IllegalStateException("The company with id " + id + " no longer exists on the server"));
		persistedCompany.setVerified(isVerified);
		return companyRepository.save(persistedCompany);
	}

	// Adding alias to company. If the alias company itself has alias, these are also added
	public Company addAliasToCompany(UUID aliasId, Company toCompany) {
		Company aliasCompany = getCompany(aliasId).orElseThrow();
		toCompany.appendAlias(aliasCompany.getName());
		aliasCompany.getAliases().forEach(alias -> toCompany.appendAlias(alias));
		return updateCompany(toCompany);
	}
}
