package org.raven.commons.data;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageImpl<T> implements Page<T> {

    private long current = 1;
    private long size = 0;
    private long total = 0;

    private List<T> items = new ArrayList<>(0);

    @Ignore
    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public static <T> PageImpl<T> of(List<T> items, long total) {
        return of(items, total, 0, 1);
    }

    public static <T> PageImpl<T> of(List<T> items, long total, long size, long current) {

        PageImpl<T> page = new PageImpl<>();
        page.setCurrent(current);
        page.setTotal(total);
        page.setItems(items);
        page.setSize(size);

        return page;
    }
}
