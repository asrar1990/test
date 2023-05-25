package com.bn.common.dto.util;

import java.util.Random;

public class RandomStringUtil
{
	private int numChars = 10;
	private static String chars	= "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public RandomStringUtil(int length) {
		numChars = length;
		if (length < 1)
			throw new IllegalArgumentException("length < 1: " + length);
	}

	public String nextString()	{
		char[] buf = new char[numChars];
		Random r = new Random();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = chars.charAt(r.nextInt(chars.length()));
		}
		return new String(buf);
	}
}