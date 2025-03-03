package org.raven.example.webmvc.model;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.raven.commons.data.Describable;
import org.raven.commons.data.ValueType;

@ApiModel("Status")
@Getter
@RequiredArgsConstructor
public enum Status implements ValueType<Integer>, Describable {
    Normal(1, "正常"), Delete(-1, "已删除");

    private final Integer value;
    private final String description;
}
