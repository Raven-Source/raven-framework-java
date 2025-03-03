package org.raven.example.springdoc.enums;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.raven.commons.data.Describable;
import org.raven.commons.data.ValueType;

@Schema(title = "状态")
@Getter
@RequiredArgsConstructor
public enum Status implements ValueType<Integer>, Describable {

    @Schema(title = "正常")
    Normal(1, "正常"),

    @Schema(title = "已删除")
    Delete(-1, "已删除");

    private final Integer value;
    private final String description;
}
