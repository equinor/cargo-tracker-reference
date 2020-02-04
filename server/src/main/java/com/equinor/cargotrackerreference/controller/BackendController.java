package com.equinor.cargotrackerreference.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.equinor.cargotrackerreference.domain.Company;
import com.equinor.cargotrackerreference.service.CompanyService;
import com.google.common.collect.Lists;

@RestController
@RequestMapping(value = "/ctref/backend")
@CrossOrigin(origins = "*")
public class BackendController {

	@Autowired
	private CompanyService companyService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public Company getAllCompanies() {
		logger.debug("Getting all companies");
//		return new CompanyList(Lists.newArrayList(companyService.getAllCompanies()));		
		return companyService.getAllCompanies().iterator().next();
	}
	
	
	@RequestMapping(value = "/company2", method = RequestMethod.GET)
	Company getAllCompanies2() {
		logger.debug("Getting all companies");
//		return new CompanyList(Lists.newArrayList(companyService.getAllCompanies()));		
		return companyService.getAllCompanies().iterator().next();
	}
	
//	public class CompanyList {
//		public List<Company> companies;
//		
//		public CompanyList() {
//			
//		}
//		
//		public CompanyList(List<Company> companies) {
//			this.companies = companies;
//		}
//	}

}
