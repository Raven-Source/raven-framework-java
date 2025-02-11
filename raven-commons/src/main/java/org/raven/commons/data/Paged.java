package org.raven.commons.data;

import java.util.List;

public interface Paged<T> {

    long getTotal();

    void setTotal(long total);

    int getSize();

    void setSize(int size);

    int getPage();

    void setPage(int page);

    List<T> getItems();

    void setItems(List<T> items);

    boolean isEmpty();

}
