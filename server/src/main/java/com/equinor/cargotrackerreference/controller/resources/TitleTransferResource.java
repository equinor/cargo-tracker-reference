package com.equinor.cargotrackerreference.controller.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.equinor.cargotrackerreference.domain.PriceBasis;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "title_transfer_rm")
@Immutable
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "companyId")
	public CompanyIdNameProperty company;

	public BigDecimal price;
	public PriceBasis priceBasis;
	public String updatedBy;
	public LocalDateTime updatedDateTime;

	@JsonIgnore
	public Integer sequence;

}
