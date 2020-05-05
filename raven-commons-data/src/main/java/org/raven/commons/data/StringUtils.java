package org.raven.commons.data;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.05.05 16:44
 */
public class StringUtils {
    private StringUtils() {
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
