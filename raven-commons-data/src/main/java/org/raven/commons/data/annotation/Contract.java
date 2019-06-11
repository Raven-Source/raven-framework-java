package org.raven.commons.data.annotation;


import org.raven.commons.data.MemberFormatType;

import java.lang.annotation.*;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * You can specify the attribute member formatting type
 */
public @interface Contract {

    /**
     * @return MemberFormatType
     */
    MemberFormatType formatType() default MemberFormatType.CamelCase;
}
