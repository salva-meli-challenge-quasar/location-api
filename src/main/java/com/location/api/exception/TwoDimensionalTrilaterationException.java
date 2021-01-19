package com.location.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TwoDimensionalTrilaterationException extends Exception {

	public TwoDimensionalTrilaterationException(String message) {
		super(message);
	}
}
