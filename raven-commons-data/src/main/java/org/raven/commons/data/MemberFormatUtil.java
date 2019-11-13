package org.raven.commons.data;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
public class MemberFormatUtil {

    /**
     * @param input
     * @param memberFormatType
     * @return
     */
    public static String namingFormat(String input, MemberFormatType memberFormatType) {

        if (input == null || input.length() == 0) {
            return input;
        }

        char first = input.charAt(0);

        if (memberFormatType == MemberFormatType.PascalCase && first >= 97 && first <= 122) {
            return convertFirstChar(input, memberFormatType);

        } else if (memberFormatType == MemberFormatType.CamelCase && first >= 65 && first <= 90) {
            return convertFirstChar(input, memberFormatType);

        } else if (memberFormatType == MemberFormatType.SnakeCase) {
            return convertSnakeCase(input, '_');

        } else if (memberFormatType == MemberFormatType.KebabCase) {
            return convertSnakeCase(input, '-');

        }

        return input;
    }

    /**
     * @param input
     * @param propertyFormatType
     * @return
     */
    private static String convertFirstChar(String input, MemberFormatType propertyFormatType) {
        char[] _temp = input.toCharArray();
        if (propertyFormatType == MemberFormatType.PascalCase) {
            _temp[0] = Character.toUpperCase(_temp[0]);
        } else {
            _temp[0] = Character.toLowerCase(_temp[0]);
        }
        return new String(_temp);
    }

    /**
     * @param input
     * @param delimiter
     * @return
     */
    private static String convertSnakeCase(String input, char delimiter) {

        int length = input.length();
        StringBuilder result = new StringBuilder(length * 2);
        int resultLength = 0;
        boolean wasPrevTranslated = false;
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            // skip first starting delimiter
            if (i > 0 || c != delimiter) {
                if (Character.isUpperCase(c)) {
                    if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != delimiter) {
                        result.append(delimiter);
                        resultLength++;
                    }
                    c = Character.toLowerCase(c);
                    wasPrevTranslated = true;
                } else {
                    wasPrevTranslated = false;
                }
                result.append(c);
                resultLength++;
            }
        }
        return resultLength > 0 ? result.toString() : input;
    }
}
