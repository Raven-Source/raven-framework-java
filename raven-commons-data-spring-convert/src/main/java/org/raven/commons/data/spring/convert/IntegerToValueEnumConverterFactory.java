package org.raven.commons.data.spring.convert;

import org.raven.commons.data.ValueEnum;
import org.raven.commons.data.ValueEnumHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.Assert;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.12.30 17:55
 */
public class IntegerToValueEnumConverterFactory implements ConverterFactory<Integer, ValueEnum> {

    /**
     * @param targetType
     * @param <T>
     * @return
     */
    @Override
    public <T extends ValueEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
        return new IntegerToValueEnumConverter(getEnumType(targetType));
    }

    /**
     * @param <T>
     */
    private class IntegerToValueEnumConverter<T extends ValueEnum> implements Converter<Integer, T> {

        private final Class<T> enumType;

        public IntegerToValueEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(Integer source) {
            return ValueEnumHelper.valueOf(enumType, source);
        }
    }

    private static Class<?> getEnumType(Class<?> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }

}
