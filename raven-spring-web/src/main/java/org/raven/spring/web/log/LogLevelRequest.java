package org.raven.spring.web.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogLevelRequest {
    @ApiModelProperty("名称 全路径名称+类名")
    String name;
    @ApiModelProperty("等级 debug info warn error")
    String level;
}
