package org.raven.commons.data.spring.convert;

import org.raven.commons.data.StringType;
import org.springframework.core.convert.converter.Converter;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.01.08 14:53
 */
public class StringTypeToStringConverter implements Converter<StringType, String> {

    /**
     * ValueType to Integer
     *
     * @param source
     * @return
     */
    @Override
    public String convert(StringType source) {

        return source == null ? null : source.getValue();
    }
}
