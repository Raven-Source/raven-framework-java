package org.raven.spring.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by yanfeng
 */
@RestController
public class HeartbeatController {

    @RequestMapping("/")
    public String ok() {
        return "ok";
    }
}
