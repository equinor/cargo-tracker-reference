package com.equinor.cargotrackerreference.controller.resources;

import java.util.UUID;

public class TradingAreaIdNameProperty {
	
	public String id;
	public String name;

	public TradingAreaIdNameProperty(UUID id, String name) {
		this.id = id != null ? id.toString() : UUID.randomUUID().toString();
		this.name = name;
	}

	public TradingAreaIdNameProperty() {
	}
	
	public UUID getId() {
		return UUID.fromString(id);
	}
	
	public void setId(UUID id) {
		this.id = id != null ? id.toString() : null;
	}
	
	@Override
	public String toString() {
		return "IdNameResource [id=" + id + ", name=" + name + "]";
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
		TradingAreaIdNameProperty other = (TradingAreaIdNameProperty) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
