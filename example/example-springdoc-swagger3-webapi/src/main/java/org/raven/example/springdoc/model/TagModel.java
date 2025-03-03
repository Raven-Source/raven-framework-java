package org.raven.example.springdoc.model;

import org.raven.example.springdoc.enums.ChannelType;
import org.raven.example.springdoc.enums.Gender;
import org.raven.example.springdoc.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.raven.commons.contract.KeyValuePair;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author by yanfeng
 * date 2021/9/26 13:58
 */
@Getter
@Setter
@Schema(title = "标签")
public class TagModel extends BaseDto<Long, Integer, String> {

    @Schema(title = "title")
    private String title;

    /**
     * 状态: 1. 正常
     */
    @NotNull(message = "启用/禁用状态不能为空")
    @Schema(title = "状态")
    private Status status;

    @Nullable
    private Gender gender;

    private KeyValuePair<Long, Box> boxKeyValuePair;

    private List<Box> boxList;

    private Box[] boxes;

    private List<ChannelType> channelTypes;
}
