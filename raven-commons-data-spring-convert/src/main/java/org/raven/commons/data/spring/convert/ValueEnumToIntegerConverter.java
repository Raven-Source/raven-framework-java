package org.raven.commons.data.spring.convert;

import org.raven.commons.data.ValueEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.01.08 14:53
 */
public class ValueEnumToIntegerConverter implements Converter<ValueEnum, Integer> {

    @Override
    public Integer convert(ValueEnum source) {
        if (source == null)
            return 0;
        else
            return source.getValue();
    }
}
