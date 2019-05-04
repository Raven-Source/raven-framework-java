package org.raven.commons.data.annotation;

import java.lang.annotation.*;

/**
 * @author yi.liang
 * @date 2018.9.25
 * @since JDK1.8
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Member {

    String value() default "";
}
