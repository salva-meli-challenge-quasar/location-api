package com.location.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchAlgorithmException extends Exception {

	public NoSuchAlgorithmException(String message) {
		super(message);
	}
	
}
