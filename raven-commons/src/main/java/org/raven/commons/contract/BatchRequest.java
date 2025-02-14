package org.raven.commons.contract;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class BatchRequest<T> {

    /**
     * 第一页，可以传0 ；下次传入上次返回数据最后一条数据的主键id/排序id
     */
    @Nullable
    private T lastId;

    /**
     * 页大小(默认值: 10)
     */
    @Min(value = 1, message = "页大小只能在 1 至 1000 之间")
    @Max(value = 1000, message = "页大小只能在 1 至 1000 之间")
    private int size = 10;


    @Deprecated
    public void setPageSize(int pageSize) {
        this.size = pageSize;
    }

}
