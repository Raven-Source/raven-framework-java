package org.raven.spring.webmvc.config;

import org.raven.spring.webmvc.interceptor.GlobalInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.spring.convert.NumberToValueTypeConverterFactory;
import org.raven.commons.data.spring.convert.StringToValueTypeConverterFactory;
import org.raven.commons.data.spring.convert.StringTypeToStringConverter;
import org.raven.commons.data.spring.convert.ValueTypeToNumberConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * @author yanfeng
 * date 2021.07.06 21:44
 */
@Slf4j
public abstract class AbstractWebConfiguration implements WebMvcConfigurer {

    @Autowired
    private GlobalInterceptor globalInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        pathMatcher.setCaseSensitive(false);
        configurer.setPathMatcher(pathMatcher);
    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new UploadFileRequestInterceptor());
//    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToValueTypeConverterFactory());
        registry.addConverterFactory(new NumberToValueTypeConverterFactory());
        registry.addConverter(new ValueTypeToNumberConverter());
        registry.addConverter(new StringTypeToStringConverter());
    }
}
