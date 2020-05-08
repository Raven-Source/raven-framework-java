package org.raven.commons.data.spring.convert;

import org.raven.commons.data.ValueType;
import org.raven.commons.data.ValueTypeUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.12.30 17:55
 */
public class NumberToValueTypeConverterFactory implements ConverterFactory<Number, ValueType> {

    /**
     * @param targetType
     * @param <T>
     * @return
     */
    @Override
    public <T extends ValueType> Converter<Number, T> getConverter(Class<T> targetType) {
        return new NumberToValueTypeConverter<>(targetType);
    }

    /**
     * @param <T>
     */
    private class NumberToValueTypeConverter<T extends ValueType> implements Converter<Number, T> {

        private final Class<T> target;

        public NumberToValueTypeConverter(Class<T> target) {
            this.target = target;
        }

        @Override
        public T convert(Number source) {
            return ValueTypeUtils.valueOf(target, source);
        }
    }

//    private static Class<?> getEnumType(Class<?> targetType) {
//        Class<?> enumType = targetType;
//        while (enumType != null && !enumType.isEnum()) {
//            enumType = enumType.getSuperclass();
//        }
//        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
//        return enumType;
//    }

}
