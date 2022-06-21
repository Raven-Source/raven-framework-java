package org.raven.commons.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author yanfeng
 */
public class NumberUtils {

    private NumberUtils() {
    }

    public static int toInt(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Integer.class)
            return (int) obj;

        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }

        return org.apache.commons.lang3.math.NumberUtils.toInt(obj.toString());

    }

    public static long toLong(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Long.class)
            return (long) obj;

        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }

        return org.apache.commons.lang3.math.NumberUtils.toLong(obj.toString());

    }

    public static double toDouble(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Double.class)
            return (double) obj;

        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }

        return org.apache.commons.lang3.math.NumberUtils.toDouble(obj.toString());

    }

    public static BigDecimal toBigDecimal(Object obj) {

        if (obj == null)
            return BigDecimal.ZERO;

        if (obj.getClass() == BigDecimal.class)
            return (BigDecimal) obj;

        return org.apache.commons.lang3.math.NumberUtils.toScaledBigDecimal(obj.toString());

    }

    public static boolean gtZero(Integer num) {
        return num != null && num > 0;
    }

    public static boolean gtZero(Long num) {
        return num != null && num > 0;
    }

    public static boolean gtZero(Short num) {
        return num != null && num > 0;
    }

    public static boolean gtZero(Float num) {
        return num != null && num > 0;
    }

    public static boolean gtZero(Double num) {
        return num != null && num > 0;
    }

    public static boolean gtZero(BigInteger num) {
        return num != null && num.compareTo(BigInteger.ZERO) > 0;
    }

    public static boolean gtZero(BigDecimal num) {
        return num != null && num.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isEmpty(Long num) {
        return num == null || num.equals(0L);
    }

    public static boolean isNotEmpty(Long num) {
        return !isEmpty(num);
    }

}
