package com.equinor.cargotrackerreference.builder;

import java.math.BigDecimal;

import com.equinor.cargotrackerreference.controller.resources.CompanyIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TitleTransferResource;
import com.equinor.cargotrackerreference.domain.PriceBasis;

public class TitleTransferBuilder {
	private TitleTransferResource titleTransferProperty;

	private TitleTransferBuilder() {
		titleTransferProperty = new TitleTransferResource();
	}

	public static TitleTransferBuilder aTitleTransferResource() {
		TitleTransferBuilder builder = new TitleTransferBuilder();
		return builder;
	}

	public TitleTransferBuilder withCompany(CompanyIdNameProperty company) {
		titleTransferProperty.company = company;
		return this;
	}

	public TitleTransferBuilder withPriceBasis(PriceBasis priceBasis) {
		titleTransferProperty.priceBasis = priceBasis;
		return this;
	}

	public TitleTransferBuilder withPrice(BigDecimal price) {
		titleTransferProperty.price = price;
		return this;
	}

	public TitleTransferResource get() {
		return titleTransferProperty;
	}

}
