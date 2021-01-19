package com.location.api.comparator;

import java.math.BigDecimal;

import com.quasar.api.core.model.Point2D;

public class Point2DComparator {

	public static boolean AreEquals(Point2D firstPoint, Point2D secondPoint) {
		if (firstPoint == secondPoint)
			return true;
		if (roundDoubleToOneDecimals(firstPoint.getX()) != roundDoubleToOneDecimals(secondPoint.getX()))
			return false;
		if (roundDoubleToOneDecimals(firstPoint.getY()) != roundDoubleToOneDecimals(secondPoint.getY()))
			return false;
		return true;
	}

	private static double roundDoubleToOneDecimals(double value) {
		return BigDecimal.valueOf(value).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
