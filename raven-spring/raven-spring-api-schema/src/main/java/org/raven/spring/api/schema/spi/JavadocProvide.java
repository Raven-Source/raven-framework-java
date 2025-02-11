package org.raven.spring.api.schema.spi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface JavadocProvide {

    MemberMutableDescribable getMethodDeclaration(Class<?> clazz, Method method);

    MemberMutableDescribable getFieldDeclaration(Class<?> clazz, Field field);

    MemberMutableDescribable getTypeDeclaration(Class<?> clazz);
}
