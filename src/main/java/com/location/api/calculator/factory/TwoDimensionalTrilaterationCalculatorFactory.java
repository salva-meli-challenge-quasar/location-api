package com.location.api.calculator.factory;

import com.location.api.calculator.LocationCalculator;
import com.location.api.calculator.TwoDimensionalTrilaterationCalculator;
import com.location.api.util.BeanUtil;

public class TwoDimensionalTrilaterationCalculatorFactory implements LocationCalculatorFactory {

	private static final int POINTS_QUANTITY_REQUIRED = 3;
	private static final int DISTANCES_QUANTITY_REQUIRED = 3;
	
	@Override
	public boolean canHandle(int pointsQuantity, int distancesQuantity) {
		return pointsQuantity == POINTS_QUANTITY_REQUIRED && distancesQuantity == DISTANCES_QUANTITY_REQUIRED;
	}
	
	@Override
	public LocationCalculator create() {
		return BeanUtil.getBean(TwoDimensionalTrilaterationCalculator.class);
	}
	
}
