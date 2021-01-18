package com.location.api.service;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.location.api.calculator.TwoDimensionalTrilaterationCalculator;
import com.location.api.comparator.Point2DComparator;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.location.api.model.Point2D;

@SpringBootTest
class TestLocatorService {

	@MockBean
	TwoDimensionalTrilaterationCalculator twoDimensionalTrilaterationCalculator;

	@Autowired
	LocatorService locatorService;

	@Test
	void testCallToTwoDimensionalTrilaterationCalculator() throws TwoDimensionalTrilaterationException {
		Point2D p1 = new Point2D(50, 0);
		Point2D p2 = new Point2D(150, -25);
		Point2D p3 = new Point2D(250, 14);
		double d1 = 150;
		double d2 = 177;
		double d3 = 225;
		Point2D expectedPoint = new Point2D(0, 0);
		when(twoDimensionalTrilaterationCalculator.calculate(p1, p2, p3, d1, d2, d3)).thenReturn(expectedPoint);
		assertTrue(Point2DComparator.AreEquals(expectedPoint, locatorService.getLocation(p1, p2, p3, d1, d2, d3)));
	}
}
