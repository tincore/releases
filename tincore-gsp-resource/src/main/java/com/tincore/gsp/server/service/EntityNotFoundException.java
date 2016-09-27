package com.tincore.gsp.server.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(UUID id) {
		super("could not find entity '" + id + "'.");
	}
}