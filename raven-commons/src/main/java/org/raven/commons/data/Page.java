package org.raven.commons.data;

import java.util.List;

public interface Page<T> {

    long getTotal();

    void setTotal(long total);

    long getSize();

    void setSize(long size);

    long getCurrent();

    void setCurrent(long current);

    List<T> getItems();

    void setItems(List<T> items);

    boolean isEmpty();

}
