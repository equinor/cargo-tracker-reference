package com.equinor.cargotrackerreference.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

public class PropertyValueAndLastUpdated<T> {

	public PropertyValueAndLastUpdated() {
		super();
	}

	@ApiModelProperty(value = "Value of the property")
	private T propertyValue;

	private LocalDateTime updatedDateTime;
	private String updatedBy;

	public PropertyValueAndLastUpdated(T value, LocalDateTime updatedDateTime, String updatedBy) {
		this.propertyValue = value;
		this.updatedDateTime = updatedDateTime;
		this.updatedBy = updatedBy;
	}

	public T getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(T value, LocalDateTime updatedDate, String updatedBy) {
		this.propertyValue = value;
		this.updatedDateTime = updatedDate;
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((propertyValue == null) ? 0 : propertyValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyValueAndLastUpdated other = (PropertyValueAndLastUpdated) obj;
		if (propertyValue == null) {
			if (other.propertyValue != null)
				return false;
		} else if (propertyValue instanceof BigDecimal && other.propertyValue instanceof BigDecimal) {
			if (((BigDecimal) propertyValue).compareTo((BigDecimal) other.propertyValue) != 0)
				return false;
		} else if (!propertyValue.equals(other.propertyValue))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "PropertyValueAndLastUpdated [propertyValue=" + propertyValue + ", updatedDate=" + updatedDateTime + "]";
	}
}
