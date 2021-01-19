package com.location.api.service;

import java.util.List;

import com.location.api.exception.MalformedDataException;
import com.location.api.exception.NoSuchAlgorithmException;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.quasar.api.core.model.Point2D;

public interface LocatorService {

	Point2D getLocation(List<Point2D> points, double[] distances)
			throws TwoDimensionalTrilaterationException, NoSuchAlgorithmException, MalformedDataException;

}
