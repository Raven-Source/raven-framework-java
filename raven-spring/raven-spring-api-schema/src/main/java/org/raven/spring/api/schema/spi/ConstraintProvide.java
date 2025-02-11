package org.raven.spring.api.schema.spi;

import java.lang.reflect.Method;

public interface ConstraintProvide {

    boolean isNullable(Class<?> clazz, Method method);
}
