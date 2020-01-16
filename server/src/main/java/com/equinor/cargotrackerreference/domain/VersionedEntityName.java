package com.equinor.cargotrackerreference.domain;

import java.util.UUID;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class VersionedEntityName extends VersionedEntity {
	
	private String name;

	public VersionedEntityName(UUID id) {
		super(id);
	}
	
	public VersionedEntityName() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return super.toString() + "VersionedEntityName [name=" + name + "]";
	}

	

}
