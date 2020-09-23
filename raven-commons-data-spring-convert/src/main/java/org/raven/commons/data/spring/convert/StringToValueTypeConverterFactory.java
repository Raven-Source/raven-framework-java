package org.raven.commons.data.spring.convert;

import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.util.StringUtils;
import org.raven.commons.data.ValueType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.12.30 17:55
 */
@Slf4j
@SuppressWarnings("unchecked")
public class StringToValueTypeConverterFactory implements ConverterFactory<String, ValueType> {

    /**
     * @param targetType
     * @param <T>
     * @return
     */
    @Override
    public <T extends ValueType> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToValueTypeConverter(targetType);
    }

    /**
     * @param <T>
     */
    private static class StringToValueTypeConverter<T extends ValueType> implements Converter<String, T> {

        private final Class<T> target;

        public StringToValueTypeConverter(Class<T> target) {
            this.target = target;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isEmpty(source)) {
                return null;
            }

            return SerializableTypeUtils.stringValueOf(target, source);
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
