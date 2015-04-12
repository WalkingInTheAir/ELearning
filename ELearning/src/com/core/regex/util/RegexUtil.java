package com.core.regex.util;

public class RegexUtil {
	private static final String numStringRex = "^[0-9]+$";
	
	public static boolean isNumStr(String str) {
		boolean result = false;
		if (null != str) {
			result = str.matches(numStringRex);
		}
		return result;
	}
	
	public static boolean isTrue(String str) {
		if (null == str || str.trim().isEmpty() || str.length() > 4) {
			return false;
		}
		str = str.toLowerCase();
		return "t".equals(str) || "true".equals(str);
	}
}
