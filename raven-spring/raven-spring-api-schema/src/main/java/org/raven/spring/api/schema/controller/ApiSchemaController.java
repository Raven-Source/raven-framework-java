package org.raven.spring.api.schema.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.raven.spring.api.schema.ApiSchema;
import org.raven.spring.api.schema.ApiSchemaContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * date 2022/9/27 17:14
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("${api.schema:/api-schema}")
public class ApiSchemaController {

    private final ApiSchemaContext apiSchemaContext;

    @GetMapping(value = "", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String apiSchemaXml() {
        return apiSchemaContext.convertToXml();
    }

    @GetMapping(value = "/raw", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ApiSchema apiSchemaJson() {
        return apiSchemaContext.getApiSchema();
    }

}
