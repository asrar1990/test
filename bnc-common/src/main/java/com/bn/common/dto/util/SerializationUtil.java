package com.bn.common.dto.util;

import java.util.Date;

public final class SerializationUtil {
    private static final String DEFAULT_STRING_VALUE = "null";
    private static final Long DEFAULT_DATE_VALUE = 0L;

    private SerializationUtil() {
    }

    public static String serialize(String v) {
        return v == null ? DEFAULT_STRING_VALUE : v.trim();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Object> T serialize(T v) {
        if(v == null) {
            return (T)DEFAULT_STRING_VALUE;
        }
        return v;
    }

    public static long serialize(Date d) {
        return d == null ? DEFAULT_DATE_VALUE : d.getTime();
    }

/*
	public static long serialize(java.sql.Date d) {
		long r = (d==null?DEFAULT_DATE_VALUE:d.getTime());
		return r;
	}
*/

    public static boolean serialize(boolean readBoolean) {
        return readBoolean;
    }

    public static long serialize(Long longValue) {
        if(longValue == null) {
            return Long.MIN_VALUE;
        }
        return longValue;
    }

    public static float serialize(Float floatValue) {
        if(floatValue == null) {
            return Float.MIN_VALUE;
        }
        return floatValue;
    }

    public static int serialize(Integer intValue) {
        if(intValue == null) {
            return Integer.MIN_VALUE;
        }
        return intValue;
    }

    public static String deserialize(String v) {
        if(DEFAULT_STRING_VALUE.equals(v)) {
            return null;
        }
        return v;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(Class<T> c, Object v) {

        if(v == null || DEFAULT_STRING_VALUE.equals(v)) {
            return null;
        }
        if(c.isAssignableFrom(v.getClass())) {
            return (T)v;
        }

        return null;
    }

    public static Date deserialize(long v) {
        Date d = null;
        if(v > 0) {
            d = new Date(v);
        }
        return d;
    }

/*
    public static java.sql.Date deserializeSqlDate(long v) {
		java.sql.Date d = null;
		if (v>0) {
			d = new java.sql.Date(v);
		}
		return d;
	}
*/

    public static boolean deserialize(boolean readBoolean) {
        return readBoolean;
    }

    public static Integer deserialize(int intValue) {
        if(intValue == Integer.MIN_VALUE) {
            return null;
        }
        return intValue;
    }

    public static Long deserializeLong(long longValue) {
        if(longValue == Long.MIN_VALUE) {
            return null;
        }
        return longValue;
    }

    public static Float deserializeFloat(float floatValue) {
        if(floatValue == Float.MIN_VALUE) {
            return null;
        }
        return floatValue;
    }

    public static Double deserializeDouble(double doubleValue) {
        if(doubleValue == Double.MIN_VALUE) {
            return null;
        }
        return doubleValue;
    }
}
