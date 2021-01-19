package com.location.api.calculator.factory;

import com.location.api.calculator.LocationCalculator;

public interface LocationCalculatorFactory {

	public boolean canHandle(int pointsQuantity, int distancesQuantity);
	public LocationCalculator create();
	
}
