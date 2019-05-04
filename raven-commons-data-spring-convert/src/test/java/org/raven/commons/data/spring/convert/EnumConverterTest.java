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
    public void convert() {

        StringToValueEnumConverterFactory factory = new StringToValueEnumConverterFactory();
        ColorType colorType = factory.getConverter(ColorType.class).convert("B");
        Assert.assertEquals(colorType, ColorType.B);

        colorType = factory.getConverter(ColorType.class).convert("4");
        Assert.assertEquals(colorType, ColorType.D);


        IntegerToValueEnumConverterFactory factory2 = new IntegerToValueEnumConverterFactory();
        colorType = factory2.getConverter(ColorType.class).convert(4);
        Assert.assertEquals(colorType, ColorType.D);
    }

}
