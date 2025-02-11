package org.raven.serializer.spring.convert;

import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.data.ValueType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.12.30 17:55
 */
public class NumberToValueTypeConverterFactory implements ConverterFactory<Number, ValueType<?>> {

    /**
     * NumberToValueTypeConverter
     *
     * @param targetType targetType
     * @param <T>        T
     * @return Converter {@link Converter}
     */
    @Override
    public <T extends ValueType<?>> Converter<Number, T> getConverter(Class<T> targetType) {
        return new NumberToValueTypeConverter<>(targetType);
    }

    /**
     * @param <T> T
     */
    private static class NumberToValueTypeConverter<T extends ValueType<?>> implements Converter<Number, T> {

        private final Class<T> target;

        public NumberToValueTypeConverter(Class<T> target) {
            this.target = target;
        }

        @Override
        public T convert(Number source) {
            return SerializableTypeUtils.valueOf(target, source);
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
