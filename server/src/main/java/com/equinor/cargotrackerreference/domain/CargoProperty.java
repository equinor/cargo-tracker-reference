package com.equinor.cargotrackerreference.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CargoProperty<T> {

	private Map<SourceSystem, PropertyValueAndLastUpdated<T>> sourcesAndPropertyValue = new HashMap<>();

	public CargoProperty() {
	}

	public CargoProperty(SourceSystem source, T value) {
		updateProperty(source, value);
	}

	public void updateProperty(SourceSystem sourceSystem, T value) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PropertyValueAndLastUpdated<T> updatedValue = new PropertyValueAndLastUpdated<T>(value, LocalDateTime.now(), auth.getName());
		if (!sourcesAndPropertyValue.containsKey(sourceSystem)) {
			sourcesAndPropertyValue.put(sourceSystem, updatedValue);
		} else {
			PropertyValueAndLastUpdated<T> existingProperty = sourcesAndPropertyValue.get(sourceSystem);
			if (!existingProperty.equals(updatedValue)) {
				existingProperty.setPropertyValue(value, LocalDateTime.now(), auth.getName());
			}
		}
	}

	@JsonIgnore
	public T getPropertyValue(SourceSystem sourceSystem) {
		T propertyValue = null;
		PropertyValueAndLastUpdated<T> propertyValueAndLastUpdated = sourcesAndPropertyValue.get(sourceSystem);
		if (propertyValueAndLastUpdated != null) {
			propertyValue = propertyValueAndLastUpdated.getPropertyValue();
		}
		return propertyValue;
	}

	/**
	 * Returns the latest updated value for property, regardless of sourcesystem
	 */
	@JsonIgnore
	public T getPropertyValue() {
		PropertyValueAndLastUpdated<T> propertyValueAndLastUpdated = null;
		for (SourceSystem sourceSystem : sourcesAndPropertyValue.keySet()) {
			if (propertyValueAndLastUpdated == null || (sourcesAndPropertyValue.get(sourceSystem) != null
					&& sourcesAndPropertyValue.get(sourceSystem).getUpdatedDateTime().isAfter(propertyValueAndLastUpdated.getUpdatedDateTime()))) {
				propertyValueAndLastUpdated = sourcesAndPropertyValue.get(sourceSystem);
			}
		}
		return propertyValueAndLastUpdated != null ? propertyValueAndLastUpdated.getPropertyValue() : null;
	}

	/**
	 * CargoTracking always wants the latest updated value from CargoTracking. If
	 * there is no value with CT as source, latest updated value is returned.
	 */
	@JsonIgnore
	public T getCargoTrackingPropertyValue() {
		T propertyValueAndLastUpdated = null;
		if (sourcesAndPropertyValue.containsKey(SourceSystem.CARGO_TRACKING)) {
			propertyValueAndLastUpdated = sourcesAndPropertyValue.get(SourceSystem.CARGO_TRACKING).getPropertyValue();
		} else {
			propertyValueAndLastUpdated = getPropertyValue();
		}

		return propertyValueAndLastUpdated;
	}

	public Map<SourceSystem, PropertyValueAndLastUpdated<T>> getSourcesAndPropertyValue() {
		return sourcesAndPropertyValue;
	}

	public void setSourcesAndPropertyValue(Map<SourceSystem, PropertyValueAndLastUpdated<T>> sourcesAndPropertyValue) {
		this.sourcesAndPropertyValue = sourcesAndPropertyValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sourcesAndPropertyValue == null) ? 0 : sourcesAndPropertyValue.hashCode());
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
		CargoProperty other = (CargoProperty) obj;
		if (sourcesAndPropertyValue == null) {
			if (other.sourcesAndPropertyValue != null)
				return false;
		} else if (!sourcesAndPropertyValue.equals(other.sourcesAndPropertyValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CargoProperty = " + sourcesAndPropertyValue;
	}

}
