package org.raven.commons.util;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class Lists {

    /**
     * Creates a new {@link ArrayList}.
     *
     * @param <E> type of elements contained in new list
     * @return a new {@link ArrayList}
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * Creates a new {@link ArrayList} filled with the contents of the given
     * {@code iterator}.
     *
     * @param elements the source iterator
     * @param <E>      type of elements contained in new list
     * @return a new {@link ArrayList}
     */
    public static <E> ArrayList<E> newArrayList(@NonNull final Iterator<? extends E> elements) {

        final ArrayList<E> list = newArrayList();
        Iterators.addAll(list, elements);
        return list;
    }

    public static <E> ArrayList<E> newArrayList(@NonNull Iterable<? extends E> elements) {
        // Let ArrayList's sizing logic work, if possible
        return (elements instanceof Collection)
                ? new ArrayList<>((Collection<? extends E>) elements)
                : newArrayList(elements.iterator());
    }

    public static <E> ArrayList<E> newArrayList(@NonNull E... elements) {
        final ArrayList<E> list = newArrayList();
        Collections.addAll(list, elements);
        return list;
    }

    private Lists() {
        // do not instantiate
    }

}
