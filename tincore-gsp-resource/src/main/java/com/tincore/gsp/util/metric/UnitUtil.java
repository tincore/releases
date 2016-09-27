package com.tincore.gsp.util.metric;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class UnitUtil {

	public static final String LABEL_RADIAN = "rad";
	public static final String LABEL_PERCENT = "%";
	public static final String LABEL_DEGREE = "º";
	public static final String LABEL_UNIT = "";

	public static double getDoubleFromString(String text) {
		return getDoubleFromString(text, (double) 0);
	}

	public static double getDoubleFromString(CharSequence text, Double defaultValue) {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMaximumFractionDigits(3);
		nf.setMinimumFractionDigits(0);
		nf.setGroupingUsed(false);
		try {
			return nf.parse(text.toString().replace(",", "")).doubleValue();
		} catch (ParseException e) {
			if (defaultValue != null){
				return defaultValue;
			}
			else {
				throw new RuntimeException(e);
			}
		}
	}

	public static String getStringFromDouble(double value, Double zeroEmpty) {
		if (zeroEmpty != null && value == zeroEmpty){
			return "";
		}
		return getStringFromDouble(value, 3, 0);
	}


	public static String getStringFromDouble(double value) {
		return getStringFromDouble(value, 3, 0);
	}

	public static String getStringFromDouble(double value, int fractionDigits, int minFractionDigits) {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setMaximumFractionDigits(fractionDigits);
		nf.setMinimumFractionDigits(minFractionDigits);
		nf.setGroupingUsed(false);
		String formatted = nf.format(value);
		// Logger.d("xxx", "convert %s %s -> %s", value, digits, formatted);
		return formatted;
	}

}
