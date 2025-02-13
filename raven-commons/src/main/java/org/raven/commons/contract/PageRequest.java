package org.raven.commons.contract;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
public class PageRequest {

    @Min(value = 1, message = "页号必须大于等于1")
    private int page = 1;

    @Min(value = 1, message = "页大小只能在 1 至 1000 之间")
    @Max(value = 1000, message = "页大小只能在 1 至 1000 之间")
    private int size = 10;

//    @ApiModelProperty(value = "排序字段")
//    private List<Sort.Order> orders;

    @Deprecated
    public void setPageNo(int pageNo) {
        this.page = pageNo;
    }

    @Deprecated
    public void setPageSize(int pageSize) {
        this.size = pageSize;
    }

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }


    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
    }

//    public static PageRequest of(int page, int size, Sort sort) {
//        return new PageRequest(page, size, sort.getOrders());
//    }

}
