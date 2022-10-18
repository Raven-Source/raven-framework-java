package org.raven.commons.data.annotation;

import org.raven.commons.data.EncryptionType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {
    EncryptionType value();
}
