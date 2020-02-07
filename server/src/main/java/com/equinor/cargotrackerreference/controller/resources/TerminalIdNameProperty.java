package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="TERMINAL")
@Immutable
public class TerminalIdNameProperty extends IdNameProperty {

	public TerminalIdNameProperty(UUID id, String name) {
		super(id, name);
	}

	public TerminalIdNameProperty() {
	}

}
