package org.raven.commons.data.annotation;


import org.raven.commons.data.MemberFormatType;

import java.lang.annotation.*;

/**
 * @author yi.liang
 * @date 2018.9.25
 * @since JDK1.8
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 可以指定属性成员格式化类型
 */
public @interface Contract {
    /**
     *
     * @return
     */
    MemberFormatType formatType() default MemberFormatType.CamelCase;
}
