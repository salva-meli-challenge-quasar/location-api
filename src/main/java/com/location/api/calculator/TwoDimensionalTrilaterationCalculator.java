package com.location.api.calculator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.location.api.exception.CircleInsideAnotherException;
import com.location.api.exception.CirclesDoNotIntersectException;
import com.location.api.exception.CirclesMatchException;
import com.location.api.exception.CollinearityException;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.location.api.model.Point2D;

@Service
public class TwoDimensionalTrilaterationCalculator {

	@Value("${two.dimensional.trilateration.calculator.error.bound}")
	private double errorBound;

	public Point2D calculate(Point2D p1, Point2D p2, Point2D p3, double d1, double d2, double d3) throws TwoDimensionalTrilaterationException {
		validateData(p1, p2, p3, d1, d2, d3);
		double distanceP1P2 = calculateDistanceBetweenPoints(p1, p2);
		double a = (Math.pow(d1, 2) - Math.pow(d2, 2) + Math.pow(distanceP1P2, 2)) / (2 * distanceP1P2);
		double height = calculateTriangleHeight(d1, a);
		List<Point2D> posibleSolutions = calculatePosibleSolutions(p1, p2,
				calculateMiddlePoint(p1, p2, distanceP1P2, a), height, distanceP1P2);
		validateCollinearityAndPosibleSolutions(p1, p2, p3, posibleSolutions);
		for (Point2D posibleSolution : posibleSolutions) {
			if (Math.abs(calculateDistanceBetweenPoints(posibleSolution, p3) - d3) < errorBound) {
				return posibleSolution;
			}
		}
		return null;
	}
	
	private void validateCollinearityAndPosibleSolutions(Point2D p1, Point2D p2, Point2D p3,
			List<Point2D> posibleSolutions) throws TwoDimensionalTrilaterationException {
		if (areCollinear(p1, p2, p3) && posibleSolutions.size() == 2
				&& (Math.abs(posibleSolutions.get(0).getX() - posibleSolutions.get(1).getX()) > errorBound
						|| Math.abs(posibleSolutions.get(0).getY() - posibleSolutions.get(1).getY()) > errorBound)) {
			throw new CollinearityException(
					"The three points are collinear: The circles formed with them and the distances intersect at more than one point");
		}
	}

	private Point2D calculateMiddlePoint(Point2D p1, Point2D p2, double distanceP1P2, double a) {
		return new Point2D(p1.getX() + a * (p2.getX() - p1.getX()) / distanceP1P2, 
				p1.getY() + a * (p2.getY() - p1.getY()) / distanceP1P2);
	}

	private List<Point2D> calculatePosibleSolutions(Point2D p1, Point2D p2, Point2D middlePoint, double height,
			double distanceP1P2) {
		List<Point2D> posibleSolutions = new ArrayList<>();
		Point2D solutionOne = new Point2D();
		solutionOne.setX(calculateOffSet(middlePoint.getX(), -(p2.getY() - p1.getY()), height, distanceP1P2));
		solutionOne.setY(calculateOffSet(middlePoint.getY(), p2.getX() - p1.getX(), height, distanceP1P2));
		Point2D solutionTwo = new Point2D();
		solutionTwo.setX(calculateOffSet(middlePoint.getX(), (p2.getY() - p1.getY()), height, distanceP1P2));
		solutionTwo.setY(calculateOffSet(middlePoint.getY(), -(p2.getX() - p1.getX()), height, distanceP1P2));
		posibleSolutions.add(solutionOne);
		posibleSolutions.add(solutionTwo);
		return posibleSolutions;
	}

	private double calculateOffSet(double middlePointCoordinate, double deltaP1P2, double height, double distanceP1P2) {
		return middlePointCoordinate + deltaP1P2 * height / distanceP1P2;
	}

	private double calculateDistanceBetweenPoints(Point2D p1, Point2D p2) {
		return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
	}

	private double calculateTriangleHeight(double hypotenuse, double base) {
		return Math.sqrt(Math.pow(hypotenuse, 2) - Math.pow(base, 2));
	}

	private void validateData(Point2D p1, Point2D p2, Point2D p3, double d1, double d2, double d3)
			throws TwoDimensionalTrilaterationException {
		validateCirclesNotContainAnother(p1, p2, p3, d1, d2, d3);
		validateCirclesDontMatch(p1, p2, p3);
		validateCirclesIntersect(p1, p2, p3, d1, d2, d3);
	}

	private void validateCirclesIntersect(Point2D p1, Point2D p2, Point2D p3, double d1, double d2, double d3)
			throws CirclesDoNotIntersectException {
		if (calculateDistanceBetweenPoints(p1, p2) > d1 + d2 || calculateDistanceBetweenPoints(p1, p3) > d1 + d3
				|| calculateDistanceBetweenPoints(p2, p3) > d2 + d3) {
			throw new CirclesDoNotIntersectException("At least two circles do not intersect");
		}
	}

	private void validateCirclesDontMatch(Point2D p1, Point2D p2, Point2D p3) throws CirclesMatchException {
		if(calculateDistanceBetweenPoints(p1, p2) == 0 || calculateDistanceBetweenPoints(p1, p3) == 0 ||
				calculateDistanceBetweenPoints(p2, p3) == 0) {
			throw new CirclesMatchException("At least two circles match, intersecting at infinite points");
		}
	}

	private void validateCirclesNotContainAnother(Point2D p1, Point2D p2, Point2D p3, double d1, double d2, double d3)
			throws CircleInsideAnotherException {
		if (calculateDistanceBetweenPoints(p1, p2) < Math.abs(d1 - d2)
				|| calculateDistanceBetweenPoints(p1, p3) < Math.abs(d1 - d3)
				|| calculateDistanceBetweenPoints(p2, p3) < Math.abs(d2 - d3)) {
			throw new CircleInsideAnotherException("At least a circle formed by a point and its distance is within another circle");
		}
	}

	private boolean areCollinear(Point2D p1, Point2D p2, Point2D p3) {
		return p1.getX() * (p2.getY() - p3.getY()) + p2.getX() * (p3.getY() - p1.getY())
				+ p3.getX() * (p1.getY() - p2.getY()) == 0;
	}
}
