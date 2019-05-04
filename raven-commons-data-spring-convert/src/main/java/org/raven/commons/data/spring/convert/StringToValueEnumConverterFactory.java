package org.raven.commons.data.spring.convert;

import org.raven.commons.data.ValueEnum;
import org.raven.commons.data.ValueEnumHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author yi.liang
 * @date 2018.12.30 17:55
 * @since JDK1.8
 */
public class StringToValueEnumConverterFactory implements ConverterFactory<String, ValueEnum> {

    @Override
    public <T extends ValueEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToValueEnumConverter(getEnumType(targetType));
    }

    private class StringToValueEnumConverter<T extends Enum & ValueEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToValueEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if(StringUtils.isEmpty(source)) {
                return null;
            }

            try {
                int value = Integer.parseInt(source);
                return ValueEnumHelper.valueOf(enumType, value);

            }catch (Exception e){

            }

            return ValueEnumHelper.nameOf(enumType, source);
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
