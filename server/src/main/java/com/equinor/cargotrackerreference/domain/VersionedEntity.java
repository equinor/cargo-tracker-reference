package com.equinor.cargotrackerreference.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class VersionedEntity {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private String id;

	@Version
	private int version;

	public VersionedEntity(UUID id) {
		this.id = id != null ? id.toString() : UUID.randomUUID().toString();
	}
	
	public VersionedEntity() {
		this.id = UUID.randomUUID().toString();
	}

	@NotNull
	@LastModifiedDate
	@CreatedDate
	private LocalDateTime updatedDateTime;

	@NotNull
	@LastModifiedBy
	@CreatedBy
	private String updatedBy;
	
	public String getId() {
		return id;
	}
	
	@JsonIgnore
	public UUID getIdAsUUID() {
		return UUID.fromString(id);
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public LocalDateTime getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		VersionedEntity other = (VersionedEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VersionedEntity [id=" + id + ", updatedDateTime=" + updatedDateTime + ", updatedBy=" + updatedBy + "]";
	}

}
