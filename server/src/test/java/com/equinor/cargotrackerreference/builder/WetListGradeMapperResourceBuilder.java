package com.equinor.cargotrackerreference.builder;

import com.equinor.cargotrackerreference.controller.resources.reporting.WetListGradeMapperResource;

public class WetListGradeMapperResourceBuilder {

	WetListGradeMapperResource wetListGradeMapperResource;
	

	private WetListGradeMapperResourceBuilder() {
		this.wetListGradeMapperResource = new WetListGradeMapperResource();
	}

	public static WetListGradeMapperResourceBuilder aGrade() {
		WetListGradeMapperResourceBuilder wetListGradeMapperResourceBuilder = new WetListGradeMapperResourceBuilder();
		return wetListGradeMapperResourceBuilder;
	}

	public WetListGradeMapperResourceBuilder withName(String name) {
		this.wetListGradeMapperResource.name = name;
		return this;
	}
	
	public WetListGradeMapperResourceBuilder withBofet(String bofet) {
		this.wetListGradeMapperResource.bofet = bofet;
		return this;
	}

	public WetListGradeMapperResourceBuilder withCountryGroup(String countryGroup) {
		this.wetListGradeMapperResource.countryGroup = countryGroup;
		return this;
	}
	
	public WetListGradeMapperResourceBuilder withCategory(String category) {
		this.wetListGradeMapperResource.category = category;
		return this;
	}

	public WetListGradeMapperResource buildGrade() {
		return wetListGradeMapperResource;
	}
}
