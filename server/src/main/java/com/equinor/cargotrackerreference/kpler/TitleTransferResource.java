package com.equinor.cargotrackerreference.kpler;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

import com.equinor.cargotracker.common.domain.PriceBasis;
import com.equinor.cargotrackerreference.controller.resources.CompanyIdNameProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TitleTransferResource {

	public TitleTransferResource() {
	}

	public TitleTransferResource(TitleTransferResource titleTransferResource) {
		this(null, titleTransferResource.company, titleTransferResource.price, titleTransferResource.priceBasis, null, null);
	}

	public TitleTransferResource(String id, CompanyIdNameProperty company, BigDecimal price, PriceBasis priceBasis, String updatedBy, LocalDateTime updatedDateTime) {
		this.id = id;
		this.company = company;
		this.price = price;
		this.priceBasis = priceBasis;
		this.updatedBy = updatedBy;
		this.updatedDateTime = updatedDateTime;
	}

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	public String id;

	public CompanyIdNameProperty company;

	public BigDecimal price;
	public PriceBasis priceBasis;
	public String updatedBy;
	public LocalDateTime updatedDateTime;

	@JsonIgnore
	public Integer sequence;

}
