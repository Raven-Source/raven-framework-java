package org.raven.commons.data;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PagedImpl<T> implements Paged<T> {

    private int page = 1;
    private int size = 0;
    private long total = 0;

    private List<T> items = new ArrayList<>(0);

    @Ignore
    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public static <T> PagedImpl<T> of(List<T> items, long total) {
        return of(items, total, 0, 1);
    }

    public static <T> PagedImpl<T> of(List<T> items, long total, int size, int page) {

        PagedImpl<T> paged = new PagedImpl<>();
        paged.setPage(page);
        paged.setTotal(total);
        paged.setItems(items);
        paged.setSize(size);

        return paged;
    }
}
