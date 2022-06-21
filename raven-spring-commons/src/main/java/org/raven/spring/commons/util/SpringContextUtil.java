package org.raven.spring.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yanfeng
 * date 2021.06.07
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private SpringContextUtil() {
    }

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name Bean name
     * @return Object
     */
    public static Object getBean(String name) throws BeansException {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz Bean class
     * @param <T>   Object T
     * @return Object
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name  Bean name
     * @param clazz Bean class
     * @param <T>   Object T
     * @return Object
     */
    public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return getApplicationContext().getBean(name, clazz);
    }
}
