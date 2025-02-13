package org.raven.commons.util;

import java.util.Collection;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }


}
