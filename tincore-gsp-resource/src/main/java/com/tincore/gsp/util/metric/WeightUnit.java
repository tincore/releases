package com.tincore.gsp.util.metric;

public enum WeightUnit implements Descriptible {

	g("g", 1), Kg("Kg", 1 / 1000D), Pound("lb", 0.00220462262), Jin("Jin", 1 / 500D);

	public static double convertFromDefault(double value, WeightUnit unit) {
		return value * unit.conversion;
	}

	public static double convertToDefault(double value, WeightUnit unit) {
		return value / unit.conversion;
	}

	public static WeightUnit getUnit() {
//		if (SharedPreferencesUtil.isUnitMetric()) {
			return WeightUnit.Kg;
//		} else {
//			return WeightUnit.Pound;
//		}
	}

	private String description;

	private double conversion;

	WeightUnit(String description, double conversion) {
		this.description = description;
		this.conversion = conversion;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
