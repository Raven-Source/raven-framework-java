package org.raven.example.springdoc.config;

import lombok.extern.slf4j.Slf4j;
import org.raven.serializer.spring.convert.NumberToValueTypeConverterFactory;
import org.raven.serializer.spring.convert.StringToValueTypeConverterFactory;
import org.raven.serializer.spring.convert.StringTypeToStringConverter;
import org.raven.serializer.spring.convert.ValueTypeToNumberConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yi.liang
 * date 2021.07.06 21:44
 */
@Configuration
@Slf4j
public class TestWebConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        pathMatcher.setCaseSensitive(false);
        configurer.setPathMatcher(pathMatcher);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToValueTypeConverterFactory());
        registry.addConverterFactory(new NumberToValueTypeConverterFactory());
        registry.addConverter(new ValueTypeToNumberConverter());
        registry.addConverter(new StringTypeToStringConverter());
    }

}
