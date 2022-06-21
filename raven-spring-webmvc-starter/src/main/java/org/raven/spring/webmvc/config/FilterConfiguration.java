package org.raven.spring.webmvc.config;

import org.raven.spring.webmvc.filters.AbstractFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author by yanfengf
 * date 2019.03.28 15:52
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public Map<String, FilterRegistrationBean<Filter>> corsFilterRegistration(@Autowired Map<String, AbstractFilter> filterMap) {

        Map<String, FilterRegistrationBean<Filter>> registrationBeanMap = new HashMap<>();

        for (Map.Entry<String, AbstractFilter> filterEntry : filterMap.entrySet()) {

            // 新建过滤器注册类
            FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
            // 添加自定义 过滤器
            registration.setFilter(filterEntry.getValue());
            // 设置过滤器的URL模式
            registration.addUrlPatterns(filterEntry.getValue().getPath());
            //设置过滤器顺序
            registration.setOrder(filterEntry.getValue().getOrder());
            registration.setName(filterEntry.getKey());

            registrationBeanMap.put(filterEntry.getKey() + "RegistrationBean", registration);
        }
        return registrationBeanMap;
    }

//    @Bean
//    public FilterRegistrationBean<Filter> globalFilterRegistration(@Autowired GlobalFilter globalFilter) {
//
//        // 新建过滤器注册类
//        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
//        // 添加自定义 过滤器
//        registration.setFilter(globalFilter);
//        // 设置过滤器的URL模式
//        registration.addUrlPatterns("/*");
//        //设置过滤器顺序
//        registration.setOrder(1);
//        registration.setName("globalFilter");
//        return registration;
//    }


}
