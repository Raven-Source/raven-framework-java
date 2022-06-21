package org.raven.spring.web.controller;

import org.raven.commons.contract.ResponseModel;
import org.raven.commons.contract.Result;
import org.raven.spring.commons.util.SpringContextUtil;
import org.raven.spring.web.api.LogApi;
import org.raven.spring.web.log.LogLevelRequest;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by yanfeng
 * date 2021/9/26 11:43
 */
@RestController
public class LogController implements LogApi {

    @Override
    public ResponseModel changeLogLevel(@RequestBody LogLevelRequest request) {
        String level = request.getLevel().toUpperCase();
        String name = request.getName();

        LogbackLoggingSystem springBootLogFile = SpringContextUtil.getBean(LogbackLoggingSystem.class);
        LogLevel logLevel = LogLevel.valueOf(level);
        springBootLogFile.setLogLevel(name, logLevel);

        return Result.ok(springBootLogFile.getLoggerConfiguration(name));
    }
}
