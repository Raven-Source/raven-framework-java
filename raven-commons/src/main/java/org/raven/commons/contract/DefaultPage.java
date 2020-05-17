package org.raven.commons.contract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.05.23 11:53
 */
public class DefaultPage<T> implements Page<T> {


    private final List<T> content = new ArrayList<>();
    private final long total;
    private final int pageSize;
    private final int pageNumber;

    public DefaultPage(List<T> content, long total, int pageSize, int pageNumber) {

        this.content.addAll(content);
        this.total = total;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    @Override
    public int getTotalPages() {
        return 0;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getTotalElements() {
        return total;
    }

    @Override
    public List<T> getContent() {
        return content;
    }
}
