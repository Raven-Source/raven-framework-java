package org.raven.example.webmvc.controller;

import org.raven.commons.contract.PageRequest;
import org.raven.example.webmvc.model.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 */
@RestController
@RequestMapping("/home")
public class HomeController {

//    @Value("${lydian.name}")
//    private String name;

    @GetMapping("/invoke")
    public String invoke() {
        return "ok: ";
    }

    @PostMapping("/invoke2")
    public Tag invoke(@Valid Tag tag) {
        return tag;
    }


    @PostMapping("/page")
    public PageRequest invoke(@Valid PageRequest pageRequest) {
        return pageRequest;
    }


}

