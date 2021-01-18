package com.location.api.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.location.api.comparator.Point2DComparator;
import com.location.api.exception.CircleInsideAnotherException;
import com.location.api.exception.CirclesDoNotIntersectException;
import com.location.api.exception.CirclesMatchException;
import com.location.api.exception.CollinearityException;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.location.api.model.Point2D;

@SpringBootTest
class TestLocatorService {

	@Autowired
	LocatorService locatorService;

	@Test
	void testTwoAlignedPointsOnYAxis() throws TwoDimensionalTrilaterationException {
		Point2D p1 = new Point2D(50, 0);
		double d1 = 250;
		Point2D p2 = new Point2D(200, 0);
		double d2 = 400;
		Point2D p3 = new Point2D(400, 200);
		double d3 = 632.455532;
		assertTrue(Point2DComparator.AreEquals(new Point2D(-200, 0), 
				locatorService.getLocation(p1, p2, p3, d1, d2, d3)));
	}

	@Test
	void testThreeAlignedPointsOnYAxisAndIntersectsInOnePoint() throws TwoDimensionalTrilaterationException {
		Point2D p1 = new Point2D(50, 0);
		double d1 = 250;
		Point2D p2 = new Point2D(200, 0);
		double d2 = 400;
		Point2D p3 = new Point2D(400, 0);
		double d3 = 600;
		Point2D expectedSolution = new Point2D(-200, 0);
		assertTrue(Point2DComparator.AreEquals(expectedSolution, 
				locatorService.getLocation(p1, p2, p3, d1, d2, d3)));
	}

	@Test
	void testTwoAlignedPointsOnXAxis() throws TwoDimensionalTrilaterationException {
		Point2D p1 = new Point2D(30, 120);
		double d1 = 318.2766093;
		Point2D p2 = new Point2D(30, 175.5);
		double d2 = 358.8875172;
		Point2D p3 = new Point2D(400, 200);
		double d3 = 670.8203932;
		Point2D expectedSolution = new Point2D(-200, -100);
		assertTrue(Point2DComparator.AreEquals(expectedSolution, 
				locatorService.getLocation(p1, p2, p3, d1, d2, d3)));
	}

	@Test
	void testThreeAlignedPointsOnXAxisAndIntersectsInMoreThanOnePoint() {
		Point2D p1 = new Point2D(25, 175);
		double d1 = 355.3167601;
		Point2D p2 = new Point2D(25, 15.1);
		double d2 = 252.7311022;
		Point2D p3 = new Point2D(25, 500);
		double d3 = 640.8002809;
		assertThrows(CollinearityException.class, () -> {
			locatorService.getLocation(p1, p2, p3, d1, d2, d3);
		});
	}
		
	@Test
	void testTwoPointsCirclesThatDoNotIntersect() {
		Point2D p1 = new Point2D(0, 50);
		double d1 = 40;
		Point2D p2 = new Point2D(0, 150);
		double d2 = 10;
		Point2D p3 = new Point2D(25, 500);
		double d3 = 350;
		assertThrows(CirclesDoNotIntersectException.class, () -> {
			locatorService.getLocation(p1, p2, p3, d1, d2, d3);
		});
	}
	
	@Test
	void testThreePointsCirclesThatDoNotIntersect() {
		Point2D p1 = new Point2D(0, 50);
		double d1 = 40;
		Point2D p2 = new Point2D(0, 150);
		double d2 = 10;
		Point2D p3 = new Point2D(25, 500);
		double d3 = 5;
		assertThrows(CirclesDoNotIntersectException.class, () -> {
			locatorService.getLocation(p1, p2, p3, d1, d2, d3);
		});
	}
	
	@Test
	void testTwoPointsCirclesThatOneIsInsideAnother() {
		Point2D p1 = new Point2D(0, 50);
		double d1 = 100;
		Point2D p2 = new Point2D(0, 150);
		double d2 = 300;
		Point2D p3 = new Point2D(25, 500);
		double d3 = 475;
		assertThrows(CircleInsideAnotherException.class, () -> {
			locatorService.getLocation(p1, p2, p3, d1, d2, d3);
		});
	}
	
	@Test
	void testTwoPointsCirclesThatMatch() {
		Point2D p1 = new Point2D(0, 50);
		double d1 = 40;
		Point2D p2 = new Point2D(0, 50);
		double d2 = 40;
		Point2D p3 = new Point2D(25, 500);
		double d3 = 5;
		assertThrows(CirclesMatchException.class, () -> {
			locatorService.getLocation(p1, p2, p3, d1, d2, d3);
		});
	}
}
