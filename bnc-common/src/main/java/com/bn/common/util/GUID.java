package com.bn.common.util;

import java.util.UUID;


public class GUID {
	
	public static String getToken() {
		return (UUID.randomUUID()).toString().replaceAll("-", "");
	}


	public static void main(String args[])
	{
		long l = System.currentTimeMillis();
		long howMany = 10000;
		
		l = System.currentTimeMillis();
		String token = null;
		for (int i=0; i < howMany; i++)
			token = GUID.getToken();
		System.out.println("uuid time:" + (System.currentTimeMillis() - l)); //main
		System.out.println(token + ":" + token.length()); //main
	}

}