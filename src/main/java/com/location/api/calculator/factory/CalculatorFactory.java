package com.location.api.calculator.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.location.api.calculator.LocationCalculator;
import com.location.api.exception.NoSuchAlgorithmException;

@Service
public class CalculatorFactory {

	private List<LocationCalculatorFactory> locationCalculatorFactories;

	public CalculatorFactory() {
		this.locationCalculatorFactories = new ArrayList<>();
		this.locationCalculatorFactories.add(new TwoDimensionalTrilaterationCalculatorFactory());
	}

	public LocationCalculator create(int pointsQuantity, int distancesQuantity) throws NoSuchAlgorithmException {
		LocationCalculatorFactory locationCalculatorFactory = detectFactory(pointsQuantity, distancesQuantity);
		if (locationCalculatorFactory == null) {
			throw new NoSuchAlgorithmException(String.format(
					"No algorithm found to work with %d points and %d distances", pointsQuantity, distancesQuantity));
		}
		return locationCalculatorFactory.create();
	}

	private LocationCalculatorFactory detectFactory(int pointsQuantity, int distancesQuantity) {
		return this.locationCalculatorFactories.stream().filter(f -> f.canHandle(pointsQuantity, distancesQuantity))
				.findAny().orElse(null);
	}
}
