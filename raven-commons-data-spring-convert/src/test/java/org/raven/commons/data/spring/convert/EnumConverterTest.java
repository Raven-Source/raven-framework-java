package org.raven.commons.data.spring.convert;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yi.liang
 * @date 2019.01.08 15:39
 * @since JDK1.8
 */
public class EnumConverterTest {

    @Test
    public void convert_enum() {

        StringToValueTypeConverterFactory factory = new StringToValueTypeConverterFactory();
        ColorType colorType = factory.getConverter(ColorType.class).convert("B");
        Assert.assertEquals(colorType, ColorType.B);

        colorType = factory.getConverter(ColorType.class).convert("4");
        Assert.assertEquals(colorType, ColorType.D);


        NumberToValueTypeConverterFactory factory2 = new NumberToValueTypeConverterFactory();
        colorType = factory2.getConverter(ColorType.class).convert(4);
        Assert.assertEquals(colorType, ColorType.D);
    }


    @Test
    public void convert_valueType() {

        StringToValueTypeConverterFactory factory = new StringToValueTypeConverterFactory();
        Gender gender = factory.getConverter(Gender.class).convert("2");
        Assert.assertEquals(gender, Gender.woman);


        NumberToValueTypeConverterFactory factory2 = new NumberToValueTypeConverterFactory();
        gender = factory2.getConverter(Gender.class).convert(2);
        Assert.assertEquals(gender, Gender.woman);
    }

}
