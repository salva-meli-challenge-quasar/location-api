package com.location.api.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.location.api.calculator.TwoDimensionalTrilaterationCalculator;
import com.location.api.comparator.Point2DComparator;
import com.location.api.exception.NoSuchAlgorithmException;
import com.quasar.api.core.model.Point2D;

@SpringBootTest
class TestLocatorService {

	@MockBean
	TwoDimensionalTrilaterationCalculator twoDimensionalTrilaterationCalculator;

	@Autowired
	LocatorService locatorService;

	@Test
	void testCallToTwoDimensionalTrilaterationCalculator() throws Exception {
		double[] distances = new double[] {150, 177, 225};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(50, 0));
		points.add(new Point2D(150, -25));
		points.add(new Point2D(250, 14));
		Point2D expectedPoint = new Point2D(0, 0);
		when(twoDimensionalTrilaterationCalculator.calculate(points, distances)).thenReturn(expectedPoint);
		assertTrue(Point2DComparator.AreEquals(expectedPoint, locatorService.getLocation(points, distances)));
	}
	
	@Test
	void testInexistentAlgorithm() throws NoSuchAlgorithmException {
		double[] distances = new double[] {40};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(0, 50));
		assertThrows(NoSuchAlgorithmException.class, () -> {
			locatorService.getLocation(points, distances);
		});
	}
}
