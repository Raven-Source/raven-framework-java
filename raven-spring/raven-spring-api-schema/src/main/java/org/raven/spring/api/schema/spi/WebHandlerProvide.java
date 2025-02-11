package org.raven.spring.api.schema.spi;

import java.lang.reflect.Method;

public interface WebHandlerProvide {

    WebHandlerDescribable getWebHandlerDeclaration(Class<?> clazz, Method method);
}
