package com.bn.common.validator;

import org.apache.commons.lang3.StringUtils;

public class Validator {
	
	public static boolean isBlankOrNull(String field) {
		return isBlank(field) || field.equals("null");
	}
	
	public static boolean isBlank(String field) {
		return field == null || field.trim().length() == 0;
	}
	
	public static boolean isInteger(String field)
	{	
		boolean isInt = false;
		if (!isBlank(field))
			try {
				Integer.parseInt(field);
				isInt = true;
				
			} catch (NumberFormatException e) {
				isInt = false;
			}
		return isInt;
	}
	
	public static boolean isDouble(String field)
	{
		boolean isDbl = false;
		if (!isBlank(field))
			try {
				Double.parseDouble(field);
				isDbl = true;
				
			} catch (NumberFormatException e) {
				isDbl = false;
			}
		return isDbl;
	}
	
	public static boolean isFloat(String field)
	{
		boolean isFloat = false;
		if (!isBlank(field))
			try {
				Float.parseFloat(field);
				isFloat = true;
				
			} catch (NumberFormatException e) {
				isFloat = false;
			}
		return isFloat;
	}
	
	public static boolean isLong(String field)
	{
		boolean isLong = false;
		if (!isBlank(field))
			try {
				Long.parseLong(field);
				isLong = true;
				
			} catch (NumberFormatException e) {
				isLong = false;
			}
		return isLong;
	}
	
	public static boolean hasBlanks(String field) {
		if (field.indexOf(' ') > -1)
			return true;
		return false;
	}
	
	public static boolean isLengthGreaterThan(String field, int len)
	{
		int l = 0;
		if (!isBlank(field)) {
			l = field.length();
		}
		
		if (l > len) {
			return true;
		} else
			return false;
	}
	
	public static boolean isLengthLessThan(String field, int len)
	{
		int l = 0;
		
		if (!isBlank(field)) {
			l = field.length();
		}
		
		if (l < len) {
			return true;
		} else
			return false;
	}
	
	public static boolean isValidEmail(String email) {
		boolean isValid = false;
		
		if (!Validator.isBlank(email)) {
			String expression = "^[-+.\\w]{1,64}@[-.\\w]{1,64}\\.[-.\\w]{2,6}$";
			if (email.matches(expression))
				isValid = true;
		}
		
		return isValid;
	}

    /**
     * Tests if a literal string is considered a "valid" EAN. An EAN is considered valid if:</p>
     * <pre>
     *    * The string contains only numerical values.
     *    * The string is exactly 13 characters long (of numerical characters).
     * </pre></p>
     *
     * This method will not attempt to {@link String#trim()} the input.
     *
     * @param ean The EAN to attempt to validate.
     *
     * @return True if the EAN is valid, else false.
     */
    public static boolean isValidEan(final String ean)
    {
        return !isBlank(ean) && ean.length() == 13 && StringUtils.isNumeric(ean);
    }

	public static boolean isValidEanForSideLoadedContent(final String ean) {
		return !isBlank(ean) && ean.length() <= 64;
	}

    public static boolean isValidCancelDate(final String cancelDate) {
     return cancelDate.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})");

    }
}
