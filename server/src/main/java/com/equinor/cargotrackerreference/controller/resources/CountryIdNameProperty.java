package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="COUNTRY")
@Immutable
public class CountryIdNameProperty extends IdNameProperty {

	public CountryIdNameProperty(UUID id, String name) {
		super(id, name);
	}

	public CountryIdNameProperty() {
	}

}
