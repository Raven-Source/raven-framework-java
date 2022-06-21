package org.raven.spring.webmvc.starter.controller;

import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/invoke")
    public String invoke() {

        
        return "ok";
    }


}

