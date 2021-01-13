package org.raven.commons.constant;

public abstract class DateFormatStringConstant {

    public final static String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public final static String[] DESERIALIZE_DATE_FORMAT_STRING = new String[]{
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss"
    };

}
