package com.equinor.cargotrackerreference.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="The resource already exists")
public class ResourceAlreadyExists extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourceAlreadyExists(String msg) {
		super(msg);
	}
}
