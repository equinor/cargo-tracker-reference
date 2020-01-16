package com.equinor.cargotrackerreference.controller.resources;

public class CountryResource extends LocationResource {
	public String regionId;

	public CountryResource() {
		super();
	}

	@Override
	public String toString() {
		return "CountryResource [regionId=" + regionId + ", id=" + id + ", name=" + name + "]";
	}

}
