package org.raven.spring.commons.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yanfeng
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private SpringContextUtils() {
    }

    /**
     * 上下文对象实例
     */
    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     */
    public static Object getBean(String name) throws BeansException {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz the class of the bean to retrieve
     * @param <T>   the type of the bean to retrieve
     * @return an instance of the bean
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name  the name of the bean to retrieve
     * @param clazz the class of the bean to retrieve
     * @param <T>   the type of the bean to retrieve
     * @return an instance of the bean
     */
    public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return getApplicationContext().getBean(name, clazz);
    }
}
