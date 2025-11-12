package org.raven.example.springdoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "大盒子", allOf = Box.class)
public class BigBox extends Box {

    @Schema(description = "批次号")
    private String batchNo;
}
