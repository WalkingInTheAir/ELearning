package com.core.regex.util;

public class RegexUtil {
	private static final String numStringRex = "^[1-9][0-9]*$";
	
	public static boolean isNumStr(String str) {
		boolean result = false;
		if (null != str) {
			result = str.matches(numStringRex);
		}
		return result;
	}
}
