package com.equinor.cargotrackerreference.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.equinor.cargotracker.common.domain.Company;
import com.equinor.cargotracker.common.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.controller.exceptions.InternalServerError;
import com.equinor.cargotrackerreference.controller.exceptions.ResourceAlreadyExists;
import com.equinor.cargotrackerreference.controller.resources.CompanyIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.CompanyResource;
import com.equinor.cargotrackerreference.controller.resources.CompanyResourceConverter;
import com.equinor.cargotrackerreference.repository.CompanyResourceRepository;
import com.equinor.cargotrackerreference.service.CompanyService;

@RestController
@RequestMapping(value = "/ctref/config")
@CrossOrigin(origins = "*")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CompanyResourceRepository companyResourceRepository;
		
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public Iterable<CompanyResource> getAllCompanies() {
		logger.debug("Getting all companies");
		return companyResourceRepository.findAll();		
	}

	@RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
	public CompanyResource getCompany(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting company with id {}", id);
		return companyResourceRepository.findById(id.toString());		
	}

	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public CompanyResource createCompany(@RequestBody CompanyResource companyResource) {
		logger.debug("Creating company: {}", companyResource);
		try {
			Company company = CompanyResourceConverter.createCompanyFromResource(companyResource);
			return CompanyResourceConverter.createCompanyResourceFromCompany(companyService.createCompany(company));
		} catch (DataIntegrityViolationException ex) {	
			String errormessage = "Unable to create company " + companyResource.name + ". Company already exists";
			logger.error(errormessage);
			throw new ResourceAlreadyExists(errormessage);
		} catch (InvalidOperationException ex) {
			logger.error("Unable to create company {}. Error: {}", companyResource, ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("Unable to create company {}. Error: {}", companyResource, ex.getMessage());
			throw new InternalServerError(ex.getMessage());			
		}
	}

	@RequestMapping(value = "/company/{id}", method = RequestMethod.PUT)
	public CompanyResource updateCompany(@PathVariable(value = "id") UUID id, @RequestBody CompanyResource companyResource) {
		logger.debug("Updating company. Id: {} Company: {}", id, companyResource);
		if (!id.equals(companyResource.getId())) {
			String errormessage = "Update failed! Id received: " + id + " is not the same as the id on the resource: " + companyResource.id;
			logger.error("Unable to update company {}. Error: {}", companyResource, errormessage);
			throw new InvalidOperationException(errormessage);
		}
		Company company = CompanyResourceConverter.createCompanyFromResource(companyResource);
		return CompanyResourceConverter.createCompanyResourceFromCompany(companyService.updateCompany(company));
	}

	@RequestMapping(value = "/company/{id}", method = RequestMethod.DELETE)
	public void cancelCompany(@PathVariable(value = "id") UUID id) {
		logger.debug("Cancelling company with id: {}", id);
		companyService.cancelCompany(id);
	}
	
	@RequestMapping(value = "/company/{oldId}/replace", method = RequestMethod.PUT)
	public CompanyResource replaceCompany(@PathVariable(value = "oldId") UUID oldId,  @RequestBody CompanyResource company) {
		logger.debug("Replacing company. Company being replaced has id: {} Replaced by: {}", oldId, company);
		CompanyIdNameProperty toCompany = new CompanyIdNameProperty(company.getId(), company.name, company.shortName);
		//TODO Check effect
		//analyticsCargoService.replaceCompanyOnCargoes(oldId, toCompany);
		companyService.cancelCompany(oldId);
		
		return company;
	}
	
	@RequestMapping(value = "/company/{oldId}/alias", method = RequestMethod.PUT)
	public CompanyResource aliasCompany(@PathVariable(value = "oldId") UUID oldId, @RequestBody CompanyResource toCompanyResource) {
		logger.debug("Setting company as alias, id: {} Should be an alias of: {}", oldId, toCompanyResource);
		
		//TODO Check effect
		//analyticsCargoService.replaceCompanyOnCargoes(oldId, new CompanyIdNameProperty(toCompanyResource.getId(), toCompanyResource.name, toCompanyResource.shortName));
		
		Company persistedCompany = companyService.addAliasToCompany(oldId, CompanyResourceConverter.createCompanyFromResource(toCompanyResource));
		
		companyService.cancelCompany(oldId);
		return CompanyResourceConverter.createCompanyResourceFromCompany(persistedCompany);
	}
	
	@RequestMapping(value = "/company/{id}/verify", method = RequestMethod.PATCH)
	public CompanyResource verifyCompany(@PathVariable(value = "id") UUID id, @RequestBody CompanyResource companyResource) {
		logger.debug("Verifying company {}", companyResource);
		return CompanyResourceConverter.createCompanyResourceFromCompany(companyService.verifyCompany(id, companyResource.verified));
	}

}
