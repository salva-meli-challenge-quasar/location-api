package com.location.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MalformedDataException extends Exception {

	public MalformedDataException(String message) {
		super(message);
	}
	
}
