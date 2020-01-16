package com.equinor.cargotrackerreference.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinor.cargotrackerreference.controller.resources.CountryResource;
import com.equinor.cargotrackerreference.controller.resources.CountryResourceConverter;
import com.equinor.cargotrackerreference.domain.Country;
import com.equinor.cargotrackerreference.repository.CountryRepository;

@Service
@Transactional
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	public Iterable<Country> getAllCountries() {
		return countryRepository.findAllByOrderByName();
	}

	public Optional<Country> getCountry(UUID id) {
		return countryRepository.findById(id.toString());
	}

	public Country patchRegionIdForCountry(UUID countryId, CountryResource country) {
		Country persistedCountry = countryRepository.findById(countryId.toString()).orElseThrow();
		UUID regionId = country.regionId != null ? UUID.fromString(country.regionId) : null; 
		persistedCountry.setRegionId(regionId);
		return countryRepository.save(persistedCountry);
	}

	public Iterable<Country> getCountriesForRegion(UUID id) {
		return countryRepository.findAllByRegionId(id.toString());
	}

	public void updateCountryWithDeletedRegionToNull(UUID id) {
		countryRepository.updateCountriesWithDeletedRegionToNull(id.toString());
	}

	public Country createCountry(CountryResource country) {
		return countryRepository.save(CountryResourceConverter.createCountryFromResource(country));
	}

	public Map<String, UUID> getCountriesAsMap() {
		Map<String, UUID> countries = new HashMap<>();
		getAllCountries().forEach(country -> countries.put(country.getName(), country.getIdAsUUID()));
		return countries;
	}
}
