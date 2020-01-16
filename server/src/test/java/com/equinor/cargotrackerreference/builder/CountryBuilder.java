package com.equinor.cargotrackerreference.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.equinor.cargotrackerreference.controller.resources.CountryResource;

public class CountryBuilder {

	private CountryResource countryResource;

	private CountryBuilder() {
		countryResource = new CountryResource();
		countryResource.name = "Dummy Terminal";
		countryResource.latitude = new BigDecimal(33.532);
		countryResource.longitude = new BigDecimal(1.2878);
		countryResource.regionId = null;
		countryResource.version = 0;
		countryResource.updatedDateTime = LocalDateTime.now();
		countryResource.updatedBy = "HLOLS";
	}

	public static CountryBuilder aCountry() {
		CountryBuilder countryBuilder = new CountryBuilder();
		return countryBuilder;
	}

	public CountryBuilder withName(String name) {
		countryResource.name = name;
		return this;
	}

	public CountryBuilder withRegionId(String countryId) {
		countryResource.regionId = countryId;
		return this;
	}

	public CountryBuilder withLatitude(BigDecimal latitude) {
		countryResource.latitude = latitude;
		return this;
	}

	public CountryBuilder withLongitude(BigDecimal longitude) {
		countryResource.longitude = longitude;
		return this;
	}

	public CountryBuilder withUpdatedBy(String updatedBy) {
		countryResource.updatedBy = updatedBy;
		return this;
	}

	public CountryBuilder withUpdatedDateTime(LocalDateTime updatedDateTime) {
		countryResource.updatedDateTime = updatedDateTime;
		return this;
	}

	public CountryResource buildCountry() {
		return countryResource;
	}
}
