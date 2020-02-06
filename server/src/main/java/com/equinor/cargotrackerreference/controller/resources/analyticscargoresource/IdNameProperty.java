package com.equinor.cargotrackerreference.controller.resources.analyticscargoresource;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.equinor.cargotracker.common.domain.Company;
import com.equinor.cargotracker.common.domain.Country;
import com.equinor.cargotracker.common.domain.Grade;
import com.equinor.cargotracker.common.domain.Region;
import com.equinor.cargotracker.common.domain.Terminal;
import com.equinor.cargotracker.common.domain.TradingArea;
import com.equinor.cargotrackerreference.controller.resources.CompanyIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.CompanyResource;
import com.equinor.cargotrackerreference.controller.resources.CountryIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeResource;
import com.equinor.cargotrackerreference.controller.resources.RegionIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TerminalIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TradingAreaIdNameProperty;

@MappedSuperclass
public abstract class IdNameProperty {
	@Id
	public String id;
	public String name;
	
	public IdNameProperty(UUID id, String name) {
		this.id = id != null ? id.toString() : null;
		this.name = name;
	}

	public IdNameProperty() {
	}

	public UUID getId() {
		return UUID.fromString(id);
	}

	public static CountryIdNameProperty createCountryReference(Country country) {
		return new CountryIdNameProperty(country.getIdAsUUID(), country.getName());
	}

	public static TerminalIdNameProperty createTerminalReference(Terminal terminal) {
		return new TerminalIdNameProperty(terminal.getIdAsUUID(), terminal.getName());
	}

	public static RegionIdNameProperty createRegionReference(Region region) {
		return new RegionIdNameProperty(region.getIdAsUUID(), region.getName());
	}

	public static GradeIdNameProperty createGradeReference(Grade grade, LocalDate date) {
		return new GradeIdNameProperty(grade.getIdAsUUID(), grade.getName(), grade.getApi(date), grade.getSulphur(date));
	}
	
	public static GradeIdNameProperty createGradeReference(GradeResource grade) {
		return new GradeIdNameProperty(grade.id, grade.name, grade.api, grade.sulphur);
	}

	public static CompanyIdNameProperty createCompanyReference(Company company) {
		return new CompanyIdNameProperty(company.getIdAsUUID(), company.getName(), company.getShortName());
	}
	
	public static CompanyIdNameProperty createCompanyReference(CompanyResource company) {
		return new CompanyIdNameProperty(company.id != null ? UUID.fromString(company.id) : null, company.name, company.shortName);
	}

	public static TradingAreaIdNameProperty createTradingAreaReference(TradingArea tradingArea) {
		return new TradingAreaIdNameProperty(tradingArea.getIdAsUUID(), tradingArea.getName());
	}

	@Override
	public String toString() {
		return "IdNameResource [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdNameProperty other = (IdNameProperty) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
