package org.raven.commons.data.annotation;

import java.lang.annotation.*;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemaIgnore {
}
