package org.raven.commons.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class Iterators {

    /**
     * Adds all the elements in the source {@code iterator} to the target
     * {@code collection}.
     *
     * <p>
     * When this method returns, the {@code iterator} will be "empty": its
     * {@code hasNext()} method returns {@code false}.
     * </p>
     *
     * @param <T> type of the elements contained inside the collection
     * @param collection target collection
     * @param iterator source
     * @return {@code true} if the target {@code collection} was modified as a
     *         result of this operation
     */
    public static <T> boolean addAll(final Collection<T> collection, final Iterator<? extends T> iterator) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= collection.add(iterator.next());
        }
        return wasModified;
    }

    private Iterators() {
        // do not instantiate
    }

}
