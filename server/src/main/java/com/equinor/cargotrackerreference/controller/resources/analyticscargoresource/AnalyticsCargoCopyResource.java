package com.equinor.cargotrackerreference.controller.resources.analyticscargoresource;

import java.util.List;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AnalyticsCargoCopy", description = "Model describing copy of Analytics Cargoes")
public class AnalyticsCargoCopyResource {

	@ApiModelProperty(value = "List of identifiers of Analytics Cargoes to copy")
	public List<UUID> ids;

	@ApiModelProperty(value = "List of properties to copy")
	public List<String> properties;

	public AnalyticsCargoCopyResource() {
	}

	public AnalyticsCargoCopyResource(List<UUID> ids, List<String> properties) {
		this.ids = ids;
		this.properties = properties;
	}
}
