package com.equinor.cargotrackerreference.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.equinor.cargotrackerreference.exceptions.InvalidOperationException;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

/**
 * A Crude Market Cargo contains information about current and planned crude cargoes.
 * Core information is:
 * - Cargo origin
 * - Cargo destination
 * - Owner(s)
 * - Grade
 * 
 * @author NIAND
 *
 */
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonStringType.class) })
@Entity
@Audited
@AuditOverride(forClass = VersionedEntity.class)
@EntityListeners(AuditingEntityListener.class)
public final class AnalyticsCargo extends VersionedEntity {

	@Type(type = "json")
	@Column(name = "CARGO", columnDefinition = "varchar")
	private Cargo cargo;

	@Enumerated(EnumType.STRING)
	private SourceSystem sourceSystem;
	private String sourceSystemIdentifier;
	private Boolean cancelled = false;
	
	public AnalyticsCargo() {
		super();
	}

	public AnalyticsCargo(String sourceSystemIdentifier, SourceSystem sourceSystem) {
		this();
		this.sourceSystemIdentifier = sourceSystemIdentifier;
		this.sourceSystem = sourceSystem;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	/**
	 * Need a special optimistic locking check for Analytics Cargo due to the way the cargoes are updated (i.e. by
	 * fetching the cargo from database, updating it and saving). 
	 * Ref: https://stackoverflow.com/questions/30881071/optimistic-locking-not-throwing-exception-when-manually-setting-version-field 
	 */
	public void setVersion(int version) {
		if (getVersion() != version) {
			throw new InvalidOperationException("Unable to save. Cargo has been updated by another user. Please refresh client.");
		}
		super.setVersion(version);
	}

	public SourceSystem getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(SourceSystem sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getSourceSystemIdentifier() {
		return sourceSystemIdentifier;
	}

	public void setSourceSystemIdentifier(String sourceSystemIdentifier) {
		this.sourceSystemIdentifier = sourceSystemIdentifier;
	}

	public Boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cancelled, cargo, sourceSystem, sourceSystemIdentifier);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnalyticsCargo other = (AnalyticsCargo) obj;
		return Objects.equals(cancelled, other.cancelled) && Objects.equals(cargo, other.cargo)
				&& sourceSystem == other.sourceSystem
				&& Objects.equals(sourceSystemIdentifier, other.sourceSystemIdentifier);
	}

	@Override
	public String toString() {
		return "AnalyticsCargo [id=" + getId() + ", cargo=" + cargo + ", sourceSystem=" + sourceSystem + ", sourceSystemIdentifier=" + sourceSystemIdentifier + "]";
	}

}
