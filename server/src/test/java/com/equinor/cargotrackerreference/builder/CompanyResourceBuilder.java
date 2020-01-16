package com.equinor.cargotrackerreference.builder;

import java.util.HashSet;
import java.util.UUID;

import com.equinor.cargotrackerreference.controller.resources.CompanyResource;

public class CompanyResourceBuilder {

	private CompanyResource companyResource;

	private CompanyResourceBuilder() {
		companyResource = new CompanyResource();
		companyResource.name = "Test company";
	}

	public static CompanyResourceBuilder aCompany() {
		CompanyResourceBuilder companyBuilder = new CompanyResourceBuilder();
		return companyBuilder;
	}

	public CompanyResourceBuilder withName(String name) {
		companyResource.name = name;
		return this;
	}
	
	public CompanyResourceBuilder withId(UUID id) {
		companyResource.id = id.toString();
		return this;
	}
	
	public CompanyResourceBuilder withVerified(boolean verified) {
		companyResource.verified = verified;
		return this;
	}
	
	public CompanyResourceBuilder addAlias(String alias) {
		if (companyResource.aliases == null) {
			companyResource.aliases = new HashSet<String>();
		} 
		
		companyResource.aliases.add(alias);					
		return this;
	}

	public CompanyResource buildCompanyResource() {
		return companyResource;
	}
}
