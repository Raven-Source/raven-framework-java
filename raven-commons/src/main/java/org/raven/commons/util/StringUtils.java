package org.raven.commons.util;

/**
 * @author yi.liang
 * date 2020.05.05 16:44
 */
public final class StringUtils {

    public static final String EMPTY = "";

    private StringUtils() {
    }

    public static boolean isNotEmpty(final CharSequence s) {
        return !isEmpty(s);
    }

    /**
     * Returns true if the parameter is null or of zero length
     *
     * @param s CharSequence
     * @return true if the parameter is null or of zero length
     */
    public static boolean isEmpty(final CharSequence s) {
        if (s == null) {
            return true;
        }
        return s.length() == 0;
    }

    public static boolean isNotBlank(final CharSequence s) {
        return !isBlank(s);
    }

    /**
     * Returns true if the parameter is null or contains only whitespace
     *
     * @param s CharSequence
     * @return true if the parameter is null or contains only whitespace
     */
    public static boolean isBlank(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasLength(final CharSequence s) {
        return (s != null && s.length() > 0);
    }

    public static boolean hasText(CharSequence str) {
        return (str != null && str.length() > 0 && containsText(str));
    }

    public static boolean containsBlanks(final CharSequence s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(String str, char searchChar) {
        return isEmpty(str) ? -1 : str.indexOf(searchChar);
    }

    public static int indexOf(String str, char searchChar, int startPos) {
        return isEmpty(str) ? -1 : str.indexOf(searchChar, startPos);
    }

    public static int indexOf(String str, String searchStr) {
        return str != null && searchStr != null ? str.indexOf(searchStr) : -1;
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
