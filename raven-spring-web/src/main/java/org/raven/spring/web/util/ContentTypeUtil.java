package org.raven.spring.web.util;

import org.apache.commons.lang3.StringUtils;

public abstract class ContentTypeUtil {

    public static boolean compareTo(String value, String contentTypeString) {
        if (!StringUtils.isEmpty(value)
                && value.toLowerCase().contains(contentTypeString)) {
            return true;
        } else return false;
    }
}
