package org.raven.commons.data.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
    String value() default "";
}
