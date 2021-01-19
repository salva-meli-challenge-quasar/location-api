package com.location.api.calculator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.location.api.comparator.Point2DComparator;
import com.location.api.exception.CircleInsideAnotherException;
import com.location.api.exception.CirclesDoNotIntersectException;
import com.location.api.exception.CirclesMatchException;
import com.location.api.exception.CollinearityException;
import com.location.api.exception.MalformedDataException;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.quasar.api.core.model.Point2D;

@SpringBootTest
class TestTwoDimensionalTrilaterationCalculator {

	@Autowired
	TwoDimensionalTrilaterationCalculator twoDimensionalTrilaterationCalculator;

	@Test
	void testTwoAlignedPointsOnYAxis() throws TwoDimensionalTrilaterationException, MalformedDataException {
		double[] distances = new double[] {250, 400, 632.455532};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(50, 0));
		points.add(new Point2D(200, 0));
		points.add(new Point2D(400, 200));
		assertTrue(Point2DComparator.AreEquals(new Point2D(-200, 0), 
				twoDimensionalTrilaterationCalculator.calculate(points, distances)));
	}

	@Test
	void testThreeAlignedPointsOnYAxisAndIntersectsInOnePoint() throws TwoDimensionalTrilaterationException, MalformedDataException {
		double[] distances = new double[] {250, 400, 600};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(50, 0));
		points.add(new Point2D(200, 0));
		points.add(new Point2D(400, 0));
		Point2D expectedSolution = new Point2D(-200, 0);
		assertTrue(Point2DComparator.AreEquals(expectedSolution, 
				twoDimensionalTrilaterationCalculator.calculate(points, distances)));
	}

	@Test
	void testTwoAlignedPointsOnXAxis() throws TwoDimensionalTrilaterationException, MalformedDataException {
		double[] distances = new double[] {318.2766093, 358.8875172, 670.8203932};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(30, 120));
		points.add(new Point2D(30, 175.5));
		points.add(new Point2D(400, 200));
		Point2D expectedSolution = new Point2D(-200, -100);
		assertTrue(Point2DComparator.AreEquals(expectedSolution, 
				twoDimensionalTrilaterationCalculator.calculate(points, distances)));
	}

	@Test
	void testThreeAlignedPointsOnXAxisAndIntersectsInMoreThanOnePoint() {
		double[] distances = new double[] {355.3167601, 252.7311022, 640.8002809};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(25, 175));
		points.add(new Point2D(25, 15.1));
		points.add(new Point2D(25, 500));
		assertThrows(CollinearityException.class, () -> {
				twoDimensionalTrilaterationCalculator.calculate(points, distances);
		});
	}
		
	@Test
	void testTwoPointsCirclesThatDoNotIntersect() {
		double[] distances = new double[] {40, 10, 350};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(0, 50));
		points.add(new Point2D(0, 150));
		points.add(new Point2D(25, 500));
		assertThrows(CirclesDoNotIntersectException.class, () -> {
				twoDimensionalTrilaterationCalculator.calculate(points, distances);
		});
	}
	
	@Test
	void testThreePointsCirclesThatDoNotIntersect() {
		double[] distances = new double[] {40, 10, 5};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(0, 50));
		points.add(new Point2D(0, 150));
		points.add(new Point2D(25, 500));
		assertThrows(CirclesDoNotIntersectException.class, () -> {
				twoDimensionalTrilaterationCalculator.calculate(points, distances);
		});
	}
	
	@Test
	void testTwoPointsCirclesThatOneIsInsideAnother() {
		double[] distances = new double[] {100, 300, 475};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(0, 50));
		points.add(new Point2D(0, 150));
		points.add(new Point2D(25, 500));
		assertThrows(CircleInsideAnotherException.class, () -> {
				twoDimensionalTrilaterationCalculator.calculate(points, distances);
		});
	}
	
	@Test
	void testTwoPointsCirclesThatMatch() {
		double[] distances = new double[] {40, 40, 5};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(0, 50));
		points.add(new Point2D(0, 50));
		points.add(new Point2D(25, 500));
		assertThrows(CirclesMatchException.class, () -> {
				twoDimensionalTrilaterationCalculator.calculate(points, distances);
		});
	}
	
	@Test
	void testNoSolutionData() {
		double[] distances = new double[] {250, 400, 632.455532};
		List<Point2D> points = new ArrayList<>();
		points.add(new Point2D(0, 0));
		points.add(new Point2D(200, 0));
		points.add(new Point2D(400, 200));
		assertThrows(MalformedDataException.class, () -> {
				twoDimensionalTrilaterationCalculator.calculate(points, distances);
		});
	}
}
