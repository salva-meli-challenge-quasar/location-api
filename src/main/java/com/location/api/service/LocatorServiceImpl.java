package com.location.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.api.calculator.TwoDimensionalTrilaterationCalculator;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.location.api.model.Point2D;

@Service
public class LocatorServiceImpl implements LocatorService {

	@Autowired
	TwoDimensionalTrilaterationCalculator twoDimensionalTrilaterationCalculator;

	public Point2D getLocation(Point2D p1, Point2D p2, Point2D p3, double d1, double d2, double d3)
			throws TwoDimensionalTrilaterationException {
		return twoDimensionalTrilaterationCalculator.calculate(p1, p2, p3, d1, d2, d3);
	}

}
