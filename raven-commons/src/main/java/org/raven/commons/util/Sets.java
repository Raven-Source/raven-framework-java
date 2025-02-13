package org.raven.commons.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public final class Sets {

    private Sets() {
        // Do not instantiate
    }

    /**
     * Creates a new HashSet filled with the given elements
     *
     * @param elements the elements to fill the new set
     * @param <E>      type of elements contained in new set
     * @return A new HasSet
     */
    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... elements) {
        final HashSet<E> set = new HashSet<>(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    /**
     * Creates a new HashSet filled with the given elements
     *
     * @param elements the elements to fill the new set
     * @param <E>      type of elements contained in new set
     * @return A new HasSet
     */
    public static <E> HashSet<E> newHashSet(Collection<? extends E> elements) {
        return new HashSet<>(elements);
    }
}
