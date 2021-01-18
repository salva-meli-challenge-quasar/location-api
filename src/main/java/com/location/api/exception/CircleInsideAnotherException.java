package com.location.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CircleInsideAnotherException extends TwoDimensionalTrilaterationException {

	public CircleInsideAnotherException(String message) {
		super(message);
	}
	
}
