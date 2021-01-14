package org.raven.commons.util;

import java.math.BigDecimal;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.04.05 19:08
 */
public class NumberUtil {

    private NumberUtil() {
    }

    public static int toInt(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Integer.class)
            return (int) obj;

        return org.apache.commons.lang3.math.NumberUtils.toInt(obj.toString());

    }

    public static long toLong(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Long.class)
            return (long) obj;

        return org.apache.commons.lang3.math.NumberUtils.toLong(obj.toString());

    }

    public static double toDouble(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Double.class)
            return (double) obj;

        return org.apache.commons.lang3.math.NumberUtils.toDouble(obj.toString());

    }

    public static BigDecimal toBigDecimal(Object obj) {

        if (obj == null)
            return BigDecimal.ZERO;

        if (obj.getClass() == BigDecimal.class)
            return (BigDecimal) obj;

        return org.apache.commons.lang3.math.NumberUtils.toScaledBigDecimal(obj.toString());

    }

    public static boolean isEmpty(Long num) {
        return num == null || num.equals(0L);
    }

    public static boolean isNotEmpty(Long num) {
        return !isEmpty(num);
    }

}
