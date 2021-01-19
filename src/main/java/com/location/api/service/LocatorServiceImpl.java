package com.location.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.api.calculator.factory.CalculatorFactory;
import com.location.api.exception.NoSuchAlgorithmException;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.quasar.api.core.model.Point2D;

@Service
public class LocatorServiceImpl implements LocatorService {

	@Autowired
	private CalculatorFactory calculatorFactory;

	public Point2D getLocation(List<Point2D> points, double[] distances)
			throws TwoDimensionalTrilaterationException, NoSuchAlgorithmException {
		return calculatorFactory.create(points.size(), distances.length).calculate(points, distances);
	}

}
