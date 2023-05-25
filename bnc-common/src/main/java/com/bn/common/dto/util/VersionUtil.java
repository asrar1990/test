package com.bn.common.dto.util;

import com.bn.common.validator.Validator;

public class VersionUtil {

	public static boolean isOneGreater(String s1, String s2) {
		int i1 = manageVersion(s1);
		int i2 = manageVersion(s2);
		return (i1 > i2 ? true : false);
	}
	
	public static boolean isOneGreaterAndEqual(String s1, String s2) {
		int i1 = manageVersion(s1);
		int i2 = manageVersion(s2);
		return (i1 >= i2 ? true : false);
	}
	
	
	public static String trimVersion(String version) {
		
		if (!Validator.isBlank(version)) {
			String[] cArry = version.split("\\.");
			if (cArry!=null && cArry.length>2) {
				int l = cArry[0].length();
				version = version.substring(0, version.indexOf('.', l+1));
			}
		}
		
		return version;
	}
	
	private static int manageVersion(String version) {
		int n = 0;
		if (!Validator.isBlank(version) && Validator.isDouble(version)) 
		{
			double d = Double.valueOf(version);
			d = d * 100000;
			n = (int) d;
		}
		return n;
	}
}
