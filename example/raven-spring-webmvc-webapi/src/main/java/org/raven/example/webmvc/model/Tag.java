package org.raven.example.webmvc.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author by yanfeng
 * date 2021/9/26 13:58
 */
@Getter
@Setter
@Accessors(chain = true)
public class Tag {

    private String title;

    @NotNull()
    @ApiModelProperty(notes = "状态")
    private Status status;

    @NotNull()
    @ApiModelProperty(notes = "pay状态")
    private PayStatus payStatus;
}
