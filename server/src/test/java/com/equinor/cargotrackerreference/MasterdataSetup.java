package com.equinor.cargotrackerreference;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.equinor.cargotracker.common.domain.Region;
import com.equinor.cargotracker.common.domain.TradingArea;
import com.equinor.cargotrackerreference.builder.CountryBuilder;
import com.equinor.cargotrackerreference.builder.GradeResourceBuilder;
import com.equinor.cargotrackerreference.builder.RegionBuilder;
import com.equinor.cargotrackerreference.builder.TerminalBuilder;
import com.equinor.cargotrackerreference.builder.TradingAreaBuilder;
import com.equinor.cargotrackerreference.controller.CompanyController;
import com.equinor.cargotrackerreference.controller.CountryController;
import com.equinor.cargotrackerreference.controller.GradeController;
import com.equinor.cargotrackerreference.controller.RegionController;
import com.equinor.cargotrackerreference.controller.TerminalController;
import com.equinor.cargotrackerreference.controller.TradingAreaController;
import com.equinor.cargotrackerreference.controller.resources.CompanyIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.CompanyResource;
import com.equinor.cargotrackerreference.controller.resources.CountryIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.CountryResource;
import com.equinor.cargotrackerreference.controller.resources.GradeIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeResource;
import com.equinor.cargotrackerreference.controller.resources.IdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.RegionIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TerminalIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TerminalResource;
import com.equinor.cargotrackerreference.controller.resources.TradingAreaIdNameProperty;

public abstract class MasterdataSetup {
	@Autowired
	private TerminalController terminalController;

	@Autowired
	private CountryController countryController;

	@Autowired
	private RegionController regionController;

	@Autowired
	private TradingAreaController tradingAreaController;

	@Autowired
	private GradeController gradeController;

	@Autowired
	private CompanyController companyController;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	protected TradingAreaIdNameProperty wafTradingAreaResource;
	protected TradingAreaIdNameProperty asiaTradingAreaResource;
	protected RegionIdNameProperty africaRegionResource;
	protected RegionIdNameProperty chinaRegionResource;

	protected TerminalIdNameProperty terminalWaf1;
	protected TerminalIdNameProperty terminalWaf2;
	protected TerminalIdNameProperty terminalChina1;
	protected TerminalIdNameProperty terminalChina2;

	protected CountryIdNameProperty sudan;
	protected CountryIdNameProperty uganda;
	protected CountryIdNameProperty trinidadTobago;
	protected CountryIdNameProperty china;
	protected CountryIdNameProperty tibet;

	protected GradeIdNameProperty wafGrade1;
	protected GradeIdNameProperty wafGrade2;
	protected GradeIdNameProperty asiaGrade1;
	protected GradeIdNameProperty asiaGrade2;

	protected CompanyIdNameProperty company1;
	protected CompanyIdNameProperty company2;
	protected CompanyIdNameProperty company3;
	protected CompanyIdNameProperty company4;
	protected CompanyIdNameProperty company5;
	protected CompanyIdNameProperty equinorCompany;
	
	public TradingAreaIdNameProperty createTradingAreaAndReference(String name) {
		TradingAreaIdNameProperty reference = new TradingAreaIdNameProperty();
		TradingArea tradingArea = TradingAreaBuilder.aTradingArea().withName(name).withIsActive(true).withUpdatedBy("HEH").buildTradingArea();
		tradingArea = tradingAreaController.createTradingArea(tradingArea);		
		reference.setId(tradingArea.getIdAsUUID());
		reference.name = tradingArea.getName();
		return reference;
	}
	
	public RegionIdNameProperty createRegionAndReference(String name) {
		RegionIdNameProperty reference = new RegionIdNameProperty();
		Region region = RegionBuilder.aRegion().withName(name).withIsActive(true).withUpdatedBy("HEH").buildRegion();
		region = regionController.createRegion(region);		
		reference.id = region.getId();
		reference.name = region.getName();
		return reference;
	}
	
	public GradeIdNameProperty createGradeAndReference(String name, TradingAreaIdNameProperty ta) {
		GradeResource grade = GradeResourceBuilder.aGrade().withName(name)
				.withApi(new BigDecimal("20.56"))
				.withSulphur(new BigDecimal("3.44"))
				.withSource("OCD")
				.withTradingAreaId(ta.getId())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		grade = gradeController.createGrade(grade);		
		return IdNameProperty.createGradeReference(grade);
	}
	
	public CompanyIdNameProperty createCompanyAndReference(String name, String shortName) {
		CompanyIdNameProperty reference = new CompanyIdNameProperty();
		CompanyResource companyResource = new CompanyResource(null, name);
		companyResource.shortName = shortName;
		CompanyResource company = companyController.createCompany(companyResource);	
		reference.id = company.id;
		reference.name = company.name;
		reference.shortName = company.shortName;
		return reference;
	}
	
	public CompanyIdNameProperty createCompanyAndReference(String name) {
		return createCompanyAndReference(name, null);
	}
	
	public IdNameProperty createCountryAndReference(String name, String regionId) {
		IdNameProperty reference = new CountryIdNameProperty();
		CountryResource country = CountryBuilder
							.aCountry()
							.withName(name)
							.withUpdatedBy("HEH")
							.withRegionId(regionId)
							.withLatitude(new BigDecimal(12.34))
							.withLongitude(new BigDecimal(3.88))
							.buildCountry();
		country = countryController.createCountry(country);		
		reference.id = country.id.toString();
		reference.name = country.name;
		return reference;
	}
	
	public IdNameProperty createTerminalAndReference(String name, String countryId) {
		IdNameProperty reference = new TerminalIdNameProperty();
		TerminalResource terminal = TerminalBuilder.aTerminal()
							.withName(name)
							.withCountryId(countryId)
							.withUpdatedBy("HEH")
							.withLatitude(new BigDecimal(12.34))
							.withLongitude(new BigDecimal(3.88))
							.buildTerminal();
		terminal = terminalController.createTerminal(terminal);	
		reference.id = terminal.id.toString();
		reference.name = terminal.name;
		return reference;
	}
	@Before
	public void setup() {
		wafTradingAreaResource = createTradingAreaAndReference("West Africa"); 
		asiaTradingAreaResource = createTradingAreaAndReference("Asia");
		africaRegionResource = createRegionAndReference("Africa");
		chinaRegionResource = createRegionAndReference("China");

		sudan = (CountryIdNameProperty) createCountryAndReference("Sudan", africaRegionResource.id);
		uganda = (CountryIdNameProperty) createCountryAndReference("Uganda", africaRegionResource.id);
		trinidadTobago = (CountryIdNameProperty) createCountryAndReference("Trinidad Tobago", africaRegionResource.id);
		terminalWaf1 = (TerminalIdNameProperty) createTerminalAndReference("Terminal1", sudan.id);
		terminalWaf2 = (TerminalIdNameProperty) createTerminalAndReference("Terminal2", uganda.id);
		terminalWaf2 = (TerminalIdNameProperty) createTerminalAndReference("Terminal2", trinidadTobago.id);
		
		wafGrade1 = createGradeAndReference("DALIA", wafTradingAreaResource);
		wafGrade2 = createGradeAndReference("TR", wafTradingAreaResource);
		
		china = (CountryIdNameProperty) createCountryAndReference("China", chinaRegionResource.id);
		tibet = (CountryIdNameProperty) createCountryAndReference("Tibet", chinaRegionResource.id);
		terminalChina1 = (TerminalIdNameProperty)createTerminalAndReference("Terminal3", china.id);
		terminalChina2 = (TerminalIdNameProperty)createTerminalAndReference("Terminal4", tibet.id);
		
		asiaGrade1 = createGradeAndReference("CHINA", asiaTradingAreaResource);
		asiaGrade2 = createGradeAndReference("TAIWAN", asiaTradingAreaResource);
		
		company1 = createCompanyAndReference("Company1");
		company2 = createCompanyAndReference("Company2");
		company3 = createCompanyAndReference("Company3");
		company4 = createCompanyAndReference("Company4");		
		company5 = createCompanyAndReference("Company5");
		equinorCompany = createCompanyAndReference("EquinorCompany", "EQUI");
	}
}
