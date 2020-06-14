package org.raven.commons.extension;

import lombok.NonNull;
import org.raven.commons.data.annotation.Description;
import org.raven.commons.util.StringUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.6.14
 */
public class DescriptionExtensions {

    public static String getDescription(@NonNull Class target) {
        Description description = (Description) target.getDeclaredAnnotation(Description.class);

        return getValue(description);
    }

    public static String getDescription(@NonNull AccessibleObject target) {
        Description description = (Description) target.getDeclaredAnnotation(Description.class);

        return getValue(description);
    }

    private static String getValue(Description description) {

        if (description != null) {
            return description.value();
        } else {
            return StringUtils.EMPTY;
        }
    }
}
