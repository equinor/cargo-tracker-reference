package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;

import com.equinor.cargotrackerreference.domain.Country;

public class CountryResourceConverter {
	public static CountryResource createResourceFromCountry(Country country) {
		if (country == null) {
			return null;
		}
		CountryResource countryResource = new CountryResource();
		countryResource.id = country.getIdAsUUID();
		countryResource.latitude = country.getLatitude();
		countryResource.longitude = country.getLongitude();
		countryResource.name = country.getName();
		countryResource.regionId = country.getRegionId() != null ? country.getRegionId().toString() : null;
		countryResource.cancelled = country.isCancelled();
		countryResource.version = country.getVersion();
		countryResource.updatedBy = country.getUpdatedBy();
		countryResource.updatedDateTime = country.getUpdatedDateTime();
		return countryResource;
	}

	public static Country createCountryFromResource(CountryResource countryResource) {
		if (countryResource == null) {
			return null;
		}
		Country country = new Country(countryResource.id);
		country.setLatitude(countryResource.latitude);
		country.setLongitude(countryResource.longitude);
		country.setName(countryResource.name);
		country.setRegionId(countryResource.regionId != null ? UUID.fromString(countryResource.regionId) : null);
		country.setCancelled(countryResource.cancelled);
		country.setVersion(countryResource.version);
		country.setUpdatedBy(countryResource.updatedBy);
		country.setUpdatedDateTime(countryResource.updatedDateTime);

		return country;
	}
}
