package org.raven.example.springdoc.controller;

import org.raven.example.springdoc.model.TagModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

public interface HomeApi {

    @Operation(description = "调用")
    @GetMapping("/invoke")
    String invoke();

    /**
     *
     * @param tag
     * @return
     */
    @Operation(summary = "调用2")
    @PostMapping("/invoke2")
    TagModel[] invoke2(@Valid TagModel[] tag);

}
