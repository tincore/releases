package com.tincore.auth.server.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserInvalidException(String userId) {
		super("Invalid user '" + userId + "'.");
	}
}