package com.location.api.service;

import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.location.api.model.Point2D;

public interface LocatorService {

	Point2D getLocation(Point2D p1, Point2D p2, Point2D p3, double d1, double d2, double d3)
			throws TwoDimensionalTrilaterationException;
}
