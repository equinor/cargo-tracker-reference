package com.equinor.cargotrackerreference.domain;

import javax.persistence.Entity;

@Entity
public class Refinery extends Location {

	private String location;
	private String owner;
	private String comments;
	private String regionId;
	
	public Refinery() {
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Refinery [location=" + location + ", owner=" + owner + ", comments=" + comments + ", regionId="
				+ regionId + "]";
	}

}
