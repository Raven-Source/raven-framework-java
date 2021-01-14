package org.raven.commons.constant;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2021/1/13 14:00:00
 */
public abstract class DateFormatStringConstant {

    public final static String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public final static String[] DESERIALIZE_DATE_FORMAT_STRING = new String[]{
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss"
    };

}
