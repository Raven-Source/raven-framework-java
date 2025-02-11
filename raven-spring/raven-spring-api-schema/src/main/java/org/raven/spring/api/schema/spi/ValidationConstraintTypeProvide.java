package org.raven.spring.api.schema.spi;


import org.raven.commons.util.ClassUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class ValidationConstraintTypeProvide implements ConstraintProvide {

    @Override
    public boolean isNullable(Class<?> clazz, Method method) {
        return ClassUtils.getMemberAnnotation(clazz, method, Nullable.class) != null;
    }

}
