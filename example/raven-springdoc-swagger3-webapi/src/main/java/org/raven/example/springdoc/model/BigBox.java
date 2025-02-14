package org.raven.example.springdoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(title = "大盒子", allOf = Box.class)
public class BigBox extends Box {

    @Schema(title = "批次号")
    private String batchNo;
}
