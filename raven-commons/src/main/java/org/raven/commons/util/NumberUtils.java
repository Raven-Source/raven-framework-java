package org.raven.commons.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.04.05 19:08
 */
public class NumberUtils {

    private NumberUtils() {
    }

    public static int toInt(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Integer.class)
            return (int) obj;

        return toInt(obj.toString());

    }

    public static int toInt(final String str) {
        return toInt(str, 0);
    }

    public static int toInt(final String str, final int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static long toLong(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Long.class)
            return (long) obj;

        return toLong(obj.toString());

    }

    public static long toLong(final String str) {
        return toLong(str, 0L);
    }

    public static long toLong(final String str, final long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static double toDouble(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Double.class)
            return (double) obj;

        return toDouble(obj.toString());

    }

    public static double toDouble(final String str) {
        return toDouble(str, 0.0d);
    }

    public static double toDouble(final String str, final double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static BigDecimal toBigDecimal(Object obj) {

        if (obj == null)
            return BigDecimal.ZERO;

        if (obj.getClass() == BigDecimal.class)
            return (BigDecimal) obj;

        return toBigDecimal(obj.toString());

    }


    public static BigDecimal toBigDecimal(final String str) {
        return toBigDecimal(str, BigDecimal.ZERO);
    }

    public static BigDecimal toBigDecimal(final String str, final BigDecimal defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return new BigDecimal(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static boolean isEmpty(Long num) {
        return num == null || num.equals(0L);
    }

    public static boolean isNotEmpty(Long num) {
        return !isEmpty(num);
    }

}
