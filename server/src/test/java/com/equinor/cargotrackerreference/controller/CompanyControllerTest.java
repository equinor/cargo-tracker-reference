
package com.equinor.cargotrackerreference.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.equinor.cargotracker.common.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.Application;
import com.equinor.cargotrackerreference.MasterdataSetup;
import com.equinor.cargotrackerreference.builder.CompanyResourceBuilder;
import com.equinor.cargotrackerreference.controller.resources.CompanyIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.CompanyResource;
import com.equinor.cargotrackerreference.controller.resources.IdNameProperty;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = { "h2" })
@WithUserDetails("user@equinor.com")
public class CompanyControllerTest extends MasterdataSetup {

	@Autowired
	private CompanyController companyController;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void createCompany() {
		IdNameProperty persistedCompany = companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Statoil").buildCompanyResource());

		assertEquals("Statoil", persistedCompany.name);
		assertNotNull(persistedCompany.id);

	}

	@Test
	public void createCompany_updateCompany_companyUpdated() {
		CompanyResource persistedCompany = companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Statoil").buildCompanyResource());

		persistedCompany.name = "Eso";
		persistedCompany = companyController.updateCompany(persistedCompany.getId(), persistedCompany);
		
		persistedCompany.name = "Esso";
		persistedCompany.aliases.add("ES");
		persistedCompany = companyController.updateCompany(persistedCompany.getId(), persistedCompany);

		assertEquals("Esso", persistedCompany.name);
		assertTrue(persistedCompany.aliases.contains("ES"));
	}

	@Test
	public void createcompany_deleteCompany_companyDeleted() {
		CompanyResource persistedCompany = companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Statoil").buildCompanyResource());
		UUID id = persistedCompany.getId();
		companyController.cancelCompany(id);
		CompanyResource company = companyController.getCompany(id);
		assertTrue(company.cancelled);

	}
		
	@Test
	public void fetchCompanyWithAndWithoutAlias() {				
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "ctref.company");

		companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Statoil").buildCompanyResource());
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Exxon").buildCompanyResource());
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName("BP").addAlias("BPNO").addAlias("BPSE").addAlias("BPA").buildCompanyResource());

		Iterator<CompanyResource> i = companyController.getAllCompanies().iterator();

		int count = 0;

		while (i.hasNext()) {
			count++;
			CompanyResource r = i.next();
			if (r.name.equals("BP")) {
				assertTrue("BPNO", r.aliases.contains("BPNO"));
			}			
		}

		assertEquals(3, count);

	}
	
	@Test
	public void createCompanies_fetchAllCompanies_companiesAreSortedByName() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "ctref.company_ocd_mapping");		
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "ctref.company");
		
		String[] companyNames = {"AA", "BP", "Exxon", "Statoil", "Zaxxon" };
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName(companyNames[1]).buildCompanyResource());
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName(companyNames[0]).buildCompanyResource());
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName(companyNames[4]).buildCompanyResource());
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName(companyNames[3]).buildCompanyResource());
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName(companyNames[2]).buildCompanyResource());

		Iterator<CompanyResource> i = companyController.getAllCompanies().iterator();

		int count = 0;
		while (i.hasNext()) {
			CompanyResource r = i.next();
			assertEquals(companyNames[count], r.name);			
			count++;
		}

		assertEquals(5, count);

	}

	@Test
	@Ignore("Not valid anymore, as alias company will be removed.")
	public void createCompanies_aliasCompany_companyIsUpdatedInCargoesAndAliasedCompanyDeleted() {
		CompanyResource persistedCompany1 = companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Company1").buildCompanyResource());
		CompanyResource persistedCompany2 = companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Company2").buildCompanyResource());
		CompanyResource persistedCompany3 = companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Company3").buildCompanyResource());
		
		CompanyIdNameProperty companyIdNameProperty3 = new CompanyIdNameProperty(UUID.fromString(persistedCompany3.id), persistedCompany3.name, null);


		// Alias company3 with company2
		companyController.aliasCompany(companyIdNameProperty3.getId(), persistedCompany2);

		// company3 should now not exist
		assertNull(companyController.getCompany(persistedCompany3.getId()));

		// company 3 should now be an alias for company 2
		assertTrue(companyController.getCompany(persistedCompany2.getId()).aliases.contains(persistedCompany3.name));

		
		// Alias company1 with company1
		companyController.aliasCompany(persistedCompany2.getId(), persistedCompany1);

		// company 3 and 2 should now not exist
		assertNull(companyController.getCompany(persistedCompany3.getId()));
		assertNull(companyController.getCompany(persistedCompany2.getId()));

		// company 3 and 2 should now be an alias for company 1
		assertTrue(companyController.getCompany(persistedCompany1.getId()).aliases.contains(persistedCompany3.name));
		assertTrue(companyController.getCompany(persistedCompany1.getId()).aliases.contains(persistedCompany2.name));

	}
			

	@Test
	public void createCompany_verifyCompany_companyIsVerified() {
		CompanyResource company = companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Company1").buildCompanyResource());
		assertFalse(company.verified);

		company.verified = true;
		company = companyController.verifyCompany(company.getId(), company);
		assertTrue(company.verified);
	}

	@Test
	public void createCompany_companyIsVeriFied_createFails() {
		exception.expect(InvalidOperationException.class);
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Company1").withVerified(true).buildCompanyResource());
	}

	@Test
	@Ignore("Not able to test with roles yet")
	public void createCompany_companyIsVeriFied_updateFailsIfUserNotSuperUser() {
		exception.expect(InvalidOperationException.class);
		companyController.createCompany(CompanyResourceBuilder.aCompany().withName("Company1").withVerified(true).buildCompanyResource());
	}
}
