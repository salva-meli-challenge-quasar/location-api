package com.location.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.location.api.exception.NoSuchAlgorithmException;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.location.api.service.LocatorService;
import com.quasar.api.core.request.LocationRequest;
import com.quasar.api.core.response.LocationResponse;

@RestController
public class LocationController {

	@Autowired
	private LocatorService locatorService;

	@PostMapping(value = "/locations", consumes = "application/json", produces = "application/json")
	public LocationResponse locate(@Valid @RequestBody LocationRequest locationRequest)
			throws TwoDimensionalTrilaterationException, NoSuchAlgorithmException {
		return new LocationResponse(locatorService.getLocation(locationRequest.getPoints(), 
				locationRequest.getDistances()));
	}
}
