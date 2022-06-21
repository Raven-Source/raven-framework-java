package org.raven.spring.commons;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Activity {

    private Long id;

    /**
     * 业务线
     */
    private Integer buId;

    /**
     * 项目
     */
    private Integer projectId;

    /**
     * 站点
     */
    private Integer siteId;

    /**
     * 分类
     */
    private Long categoryId;

    /**
     * 关联id，如：促销规则 Promotion#Id
     */
    private List<Long> resIds = new ArrayList<>();

    /**
     * 名称
     */
    private String name;

    //region 时间段

    /**
     * 时区（如：+08:00）
     */
    private String timezone;

    /**
     * 开始时间
     */
    private Date startTime = new Date();

    /**
     * 结束时间
     */
    private Date endTime = new Date();

}
