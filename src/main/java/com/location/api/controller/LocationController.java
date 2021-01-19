package com.location.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.location.api.exception.NoSuchAlgorithmException;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.location.api.service.LocatorService;
import com.quasar.api.core.model.Point2D;
import com.quasar.api.core.request.LocationRequest;
import com.quasar.api.core.response.LocationResponse;

@RestController
public class LocationController {

	@Autowired
	LocatorService locatorService;

	@PostMapping(value = "/location", consumes = "application/json", produces = "application/json")
	public LocationResponse locate(@RequestBody LocationRequest locationData)
			throws TwoDimensionalTrilaterationException, NoSuchAlgorithmException {
		Point2D location = locatorService.getLocation(locationData.getPoints(), locationData.getDistances());
		return new LocationResponse(location);
	}
}
