package org.raven.example.springdoc.controller;

import org.raven.commons.contract.*;
import org.raven.example.springdoc.model.BigBox;
import org.raven.example.springdoc.model.Box;
import org.raven.example.springdoc.model.TagModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Tag(name = "Home", description = "首页")
@RestController
@RequestMapping("/home")
public class HomeController implements HomeApi {

    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private void setHandlerMapping(
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public String invoke() {
        return "ok";
    }

    @Override
    public TagModel[] invoke2(@Valid TagModel[] tag) {
        return tag;
    }

    @PostMapping("/page3")
    public ResponseModel<List<TagModel>> page3(@Valid PageRequest pageRequest) {
        return new ResponseModel<>();
    }

    @Operation(description = "获取page list")
    @PostMapping("/page4")
    public ResponseModel<PageResponse<Box>> page4(@Valid PageRequest pageRequest) {
        return new ResponseModel<>();
    }

    @PostMapping("/list")
    public List<String> list(List<Long> ids) {
        return ids.stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Operation(description = "获取list2")
    @PostMapping("/list2")
    public BatchResponse<BigBox, Long> list2(BatchRequest<Long> request) {
        return new BatchResponse<>(null, request.getLastId());
    }


}

