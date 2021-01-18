package com.location.comparator;

import java.math.BigDecimal;

import com.location.api.model.Point2D;

public class Point2DComparator {

	public static boolean AreEquals(Point2D firstPoint, Point2D secondPoint) {
		if (firstPoint == secondPoint)
			return true;
		if (roundDoubleToTwoDecimals(firstPoint.getX()) != roundDoubleToTwoDecimals(secondPoint.getX()))
			return false;
		if (roundDoubleToTwoDecimals(firstPoint.getY()) != roundDoubleToTwoDecimals(secondPoint.getY()))
			return false;
		return true;
	}

	private static double roundDoubleToTwoDecimals(double value) {
		return BigDecimal.valueOf(value).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
