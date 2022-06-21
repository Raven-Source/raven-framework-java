package org.raven.spring.webmvc.starter.model;


import io.swagger.annotations.ApiModel;
import org.raven.commons.data.ValueType;

@ApiModel("Status")
public enum Status implements ValueType<Integer> {
    Normal(1), Delete(-1);

    private final int value;

    @Override
    public Integer getValue() {
        return value;
    }

    Status(int value) {
        this.value = value;
    }
}
