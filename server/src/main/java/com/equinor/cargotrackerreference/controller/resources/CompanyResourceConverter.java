package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;

import com.equinor.cargotrackerreference.domain.Company;

public class CompanyResourceConverter {
	
	public static Company createCompanyFromResource(CompanyResource companyResource) {
		if (companyResource == null) {
			return null;
		}
		Company company = new Company(companyResource.id == null ? UUID.randomUUID() : UUID.fromString(companyResource.id));
		company.setName(companyResource.name);
		company.setVersion(companyResource.version);
		company.setVerified(companyResource.verified);
		company.setCancelled(companyResource.cancelled);
		company.setShortName(companyResource.shortName);
		
		if (companyResource.aliases != null) {
			company.setAliases(companyResource.aliases);
		}

		return company;
	}
	
	public static CompanyResource createCompanyResourceFromCompany(Company company) {
		if (company == null) {
			return null;
		}
		CompanyResource companyResource = new CompanyResource(company.getIdAsUUID(), company.getName());
		companyResource.verified = company.isVerified();
		companyResource.cancelled = company.isCancelled();
		companyResource.version = company.getVersion();
		companyResource.aliases = company.getAliases();
		companyResource.shortName = company.getShortName();

		return companyResource;
	}
}
