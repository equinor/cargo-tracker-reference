package com.equinor.cargotrackerreference.readmodel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.equinor.cargotracker.common.domain.PriceBasis;
import com.equinor.cargotracker.common.domain.TitleTransfer;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "title_transfer_rm")
@Audited
public class TitleTransferRm {
	public TitleTransferRm(TitleTransfer titleTransfer, Integer sequence) {
		this.id = titleTransfer.getId().toString();
		this.companyId = titleTransfer.getCompany() != null ? titleTransfer.getCompany().getIdAsUUID().toString() : null;
		this.price = titleTransfer.getPrice();
		this.priceBasis = titleTransfer.getPriceBasis();
		this.sequence = sequence;
		this.updatedBy = titleTransfer.getUpdatedBy();
		this.updatedDateTime = titleTransfer.getUpdatedDateTime();
	}

	public TitleTransferRm() {
	}

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	public String id;

	public String companyId;
	public BigDecimal price;
	public PriceBasis priceBasis;
	public String updatedBy;
	public LocalDateTime updatedDateTime;

	@JsonIgnore
	public Integer sequence;

	@Override
	public String toString() {
		return "TitleTransferRm [id=" + id + ", companyId=" + companyId + ", price=" + price + ", priceBasis="
				+ priceBasis + "]";
	}
}
