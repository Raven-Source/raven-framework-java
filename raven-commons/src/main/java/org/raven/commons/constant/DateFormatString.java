package org.raven.commons.constant;

/**
 * @author yanfeng
 * date 2021.06.16
 */
public abstract class DateFormatString {

    public final static String ISO_LOCAL_DATE = "yyyy-MM-dd";

    public final static String ISO_LOCAL_TIME = "HH:mm:ss.SSS";

    public final static String ISO_OFFSET_DATE = "yyyy-MM-ddXXX";

    public final static String ISO_OFFSET_TIME = "HH:mm:ss.SSSXXX";

    public final static String ISO_LOCAL_DATE_TIME = "yyyy-MM-dd HH:mm:ss.SSS";

    public final static String ISO_LOCAL_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";

    public final static String ISO_OFFSET_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public final static String ISO_OFFSET_DATE_TIME_SECOND = "yyyy-MM-dd'T'HH:mm:ssXXX";

    public final static String ISO_ZONED_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public final static String ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public final static String RFC822_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public final static String RFC822_DATE_TIME_SECOND = "yyyy-MM-dd'T'HH:mm:ssZ";

    public final static String[] DESERIALIZE_DATE_FORMAT_STRING = new String[]{
            ISO_OFFSET_DATE_TIME
            , ISO_OFFSET_DATE_TIME_SECOND
            , ISO_LOCAL_DATE
            , ISO_LOCAL_DATE_TIME
            , ISO_LOCAL_DATE_TIME_SECOND
            , RFC822_DATE_TIME
            , RFC822_DATE_TIME_SECOND
    };
}
