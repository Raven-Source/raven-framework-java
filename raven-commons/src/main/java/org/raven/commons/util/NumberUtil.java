package org.raven.commons.util;

import java.math.BigDecimal;

/**
 * @author yi.liang
 * @date 2019.04.05 19:08
 * @since JDK1.8
 */
public class NumberUtil {

    private NumberUtil() {
    }

    /**
     * @param obj
     * @return
     */
    public static int toInt(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Integer.class)
            return (int) obj;

        return org.apache.commons.lang3.math.NumberUtils.toInt(obj.toString());

    }

    /**
     * @param obj
     * @return
     */
    public static long toLong(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Long.class)
            return (long) obj;

        return org.apache.commons.lang3.math.NumberUtils.toLong(obj.toString());

    }

    /**
     * @param obj
     * @return
     */
    public static double toDouble(Object obj) {

        if (obj == null)
            return 0;

        if (obj.getClass() == Double.class)
            return (double) obj;

        return org.apache.commons.lang3.math.NumberUtils.toDouble(obj.toString());

    }

    /**
     *  保留2位小数 四舍五入
     * @param obj
     * @return
     */
    public static BigDecimal toBigDecimal(Object obj) {

        if (obj == null)
            return BigDecimal.ZERO;

        if (obj.getClass() == BigDecimal.class)
            return (BigDecimal) obj;

        return org.apache.commons.lang3.math.NumberUtils.toScaledBigDecimal(obj.toString());

    }

    /**
     * 校验Long非空
     *
     * @param num
     * @return
     */
    public static boolean isEmpty(Long num) {
        if (num == null || num.equals(0L)) {
            return true;
        }
        return false;
    }

    /**
     * @param num
     * @return
     */
    public static boolean isNotEmpty(Long num) {
        return !isEmpty(num);
    }

}
