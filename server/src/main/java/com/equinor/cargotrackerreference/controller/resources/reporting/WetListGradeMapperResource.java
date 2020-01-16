package com.equinor.cargotrackerreference.controller.resources.reporting;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "WET_LIST_GRADE_MAPPER")
@Immutable
@JsonSerialize()
@ApiModel(value = "WetListGradeMapperResource", description = "Mapper of grade for use in the wetlist report")
public class WetListGradeMapperResource {
	public WetListGradeMapperResource() {
	}
	
	public String getGradeId() {
		return gradeId;
	}
	@Id
	public String id;
	public String gradeId;
	public String bofet;
	public String countryGroup;
	public String name;
	public String category;
}
