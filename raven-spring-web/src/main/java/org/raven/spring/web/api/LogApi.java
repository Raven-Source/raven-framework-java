package org.raven.spring.web.api;

import org.raven.commons.contract.ResponseModel;
import org.raven.spring.web.log.LogLevelRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * date 2021/8/18 2:15 下午
 */

@RequestMapping("/log")
public interface LogApi {

    /**
     * 改变日志等级的log
     * @param params LogLevelRequest
     */
    @PostMapping("/change")
    ResponseModel changeLogLevel(LogLevelRequest params);
}
