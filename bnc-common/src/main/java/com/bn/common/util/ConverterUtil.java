package com.bn.common.util;

import com.bn.common.validator.Validator;

public final class ConverterUtil {

    /**
     * Prevent object use
     */
    private ConverterUtil() {
    }

    public static int convertToInt(String value) {
		int result = 0;
		if (!Validator.isBlank(value) && Validator.isInteger(value))
			result = new Integer(value).intValue();
			
		return result;
	}
	
	public static long convertToLong(String value) {
		long result = 0;
		if (!Validator.isBlank(value) && Validator.isLong(value))
			result = new Long(value);
			
		return result;
	}
}
