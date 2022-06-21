package org.raven.commons.constant;

/**
 * @author yanfeng
 * date 2021.06.16
 */
public abstract class DateFormatStringConstant {

    public final static String ISO_LOCAL_DATE = "yyyy-MM-dd";

    public final static String ISO_OFFSET_DATE = "yyyy-MM-ddXXX";

    public final static String ISO_DATE = "yyyy-MM-ddXXX";

    public final static String ISO_LOCAL_TIME = "HH:mm:ss.SSS";

    public final static String ISO_OFFSET_TIME = "HH:mm:ss.SSSXXX";

    public final static String ISO_TIME = "HH:mm:ss.SSSXXX";

    public final static String ISO_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public final static String ISO_OFFSET_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public final static String ISO_ZONED_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public final static String ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public final static String ISO_INSTANT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public final static String ISO_INSTANT_SECOND = "yyyy-MM-dd'T'HH:mm:ssZ";

    public final static String DATE_TIME = "yyyy-MM-dd HH:mm:ss.SSS";

    public final static String DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";

    public final static String[] DESERIALIZE_DATE_FORMAT_STRING = new String[]{
            ISO_OFFSET_DATE_TIME, ISO_INSTANT, ISO_INSTANT_SECOND
    };

}
