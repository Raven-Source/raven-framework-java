package org.raven.serializer.spring.convert;

import org.raven.commons.data.ValueType;
import org.springframework.core.convert.converter.Converter;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.01.08 14:53
 */
public class ValueTypeToNumberConverter implements Converter<ValueType<?>, Number> {

    /**
     * ValueType to Integer
     *
     * @param source source
     * @return Number {@link Number}
     */
    @Override
    public Number convert(ValueType<?> source) {
        if (source == null)
            return 0;
        else
            return source.getValue();
    }
}
