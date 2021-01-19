package com.location.api.calculator;

import java.util.List;

import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.quasar.api.core.model.Point2D;

public interface LocationCalculator {

	public Point2D calculate(List<Point2D> points, double[] distances) throws TwoDimensionalTrilaterationException;
	
}
