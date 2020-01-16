package com.equinor.cargotrackerreference.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class TitleTransfer {
	private UUID id;
	private Company company;
	private BigDecimal price;
	private PriceBasis priceBasis;

	private String updatedBy;
	private LocalDateTime updatedDateTime;

	public TitleTransfer() {
	}

	public TitleTransfer(UUID id, Company company, BigDecimal price, PriceBasis priceBasis, String updatedBy, LocalDateTime updatedDateTime) {
		this.id = id == null ? UUID.randomUUID() : id;
		this.company = company;
		this.price = price;
		this.priceBasis = priceBasis;
		this.updatedBy = updatedBy;
		this.updatedDateTime = updatedDateTime;
	}

	public UUID getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public PriceBasis getPriceBasis() {
		return priceBasis;
	}

	public void setPriceBasis(PriceBasis priceBasis) {
		this.priceBasis = priceBasis;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, id, price, priceBasis);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TitleTransfer other = (TitleTransfer) obj;
        if (price == null && other.price != null) {
        	return false;
        } else if (price != null && other.price == null) {
            return false;
        } else if (price != null && other.price != null && price.compareTo(other.price) != 0)
            return false;
		return Objects.equals(company, other.company) && Objects.equals(id, other.id)
				&& priceBasis == other.priceBasis;
	}

	@Override
	public String toString() {
		return "TitleTransfer [company=" + company + ", price=" + price + ", priceBasis=" + priceBasis + "]";
	}


}
