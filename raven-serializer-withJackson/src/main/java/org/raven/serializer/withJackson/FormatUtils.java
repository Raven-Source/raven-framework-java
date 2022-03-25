//package org.raven.serializer.withJackson;
//
//import org.raven.commons.data.MemberFormatType;
//
///**
// * StringUtils
// *
// * @author yi.liang
// * @since JDK1.8
// * date 2018/1/3 14:00:00
// */
//public class FormatUtils {
//
//    /**
//     * naming Format
//     *
//     * @param value              value
//     * @param propertyFormatType propertyFormatType
//     * @return String
//     */
//    public static String namingFormat(String value, MemberFormatType propertyFormatType) {
//
//        char first = value.charAt(0);
//
//        if (propertyFormatType == MemberFormatType.PascalCase && first >= 97 && first <= 122) {
//
//            return convertFirstChar(value, propertyFormatType);
//
//        } else if (propertyFormatType == MemberFormatType.CamelCase && first >= 65 && first <= 90) {
//
//            return convertFirstChar(value, propertyFormatType);
//        }
//
//        return value;
//    }
//
//    /**
//     * @param value              value
//     * @param propertyFormatType propertyFormatType
//     * @return String
//     */
//    private static String convertFirstChar(String value, MemberFormatType propertyFormatType) {
//        char[] _temp = value.toCharArray();
//        if (propertyFormatType == MemberFormatType.PascalCase) {
//            _temp[0] = Character.toUpperCase(_temp[0]);
//        } else {
//            _temp[0] = Character.toLowerCase(_temp[0]);
//        }
//        return new String(_temp);
//    }
//
//}
