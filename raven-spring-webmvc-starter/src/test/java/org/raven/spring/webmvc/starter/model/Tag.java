package org.raven.spring.webmvc.starter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author by yanfeng
 * date 2021/9/26 13:58
 */
@Getter
@Setter
@Accessors(chain = true)
public class Tag {

    private String title;

    private Status status;
}
