package com.location.api.service;

import java.util.List;

import com.quasar.api.core.model.Point2D;

public interface LocatorService {

	Point2D getLocation(List<Point2D> points, double[] distances) throws Exception;
	
}
