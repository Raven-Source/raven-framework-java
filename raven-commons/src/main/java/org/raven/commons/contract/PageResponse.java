package org.raven.commons.contract;

import lombok.NoArgsConstructor;
import org.raven.commons.data.PagedImpl;

import java.util.List;

/**
 * @author wangqiuhua
 * date 2022/6/29 15:14
 */
@NoArgsConstructor
public class PageResponse<T> extends PagedImpl<T> {

    public PageResponse(long total, List<T> items) {
        setTotal(total);
        setItems(items);
    }

    public PageResponse(int page, int size, long total, List<T> items) {
        setPage(page);
        setSize(size);
        setTotal(total);
        setItems(items);
    }

    public static <T> PageResponse<T> of(long total, List<T> items) {
        return of(1, 0, total, items);
    }

    public static <T> PageResponse<T> of(int page, int size, long total, List<T> items) {

        return new PageResponse<>(page, size, total, items);
    }

}
