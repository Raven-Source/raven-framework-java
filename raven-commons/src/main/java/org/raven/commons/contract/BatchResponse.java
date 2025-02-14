package org.raven.commons.contract;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BatchResponse<T, K> {

    /**
     * total
     */
    private long total;

    /**
     * 页数据集合
     */
    private List<T> items;

    /**
     * 返回数据最后一条数据的主键id/排序id
     */
    private K lastId;


    public BatchResponse() {
        this(0);
    }


    public BatchResponse(long total) {
        this(new ArrayList<>(), null, total);
    }

    public BatchResponse(List<T> items, K lastId) {
        this(items, lastId, 0L);
    }

    public BatchResponse(List<T> items, K lastId, long total) {
        this.items = items;
        this.lastId = lastId;
        this.total = total;
    }

    public static <T, K> BatchResponse<T, K> of(long total) {
        return new BatchResponse<>(total);
    }

    public static <T, K> BatchResponse<T, K> of(List<T> item, K lastId) {
        return new BatchResponse<>(item, lastId, 0L);
    }

    public static <T, K> BatchResponse<T, K> of(List<T> item, K lastId, long total) {
        return new BatchResponse<>(item, lastId, total);
    }
}
