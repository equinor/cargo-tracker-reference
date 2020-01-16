package com.equinor.cargotrackerreference.controller.resources;

import java.util.Iterator;

import com.equinor.cargotrackerreference.domain.Country;

public class CountryResourceIterator implements Iterator<CountryResource>, Iterable<CountryResource> {

	private Iterator<? extends Country> locationIterator;

	public CountryResourceIterator(Iterator<? extends Country> analyticsCargoIterator) {
		this.locationIterator = analyticsCargoIterator;
	}

	@Override
	public boolean hasNext() {
		return locationIterator.hasNext();
	}

	@Override
	public CountryResource next() {
		return CountryResourceConverter.createResourceFromCountry(locationIterator.next());
	}

	@Override
	public Iterator<CountryResource> iterator() {
		return this;
	}

}
