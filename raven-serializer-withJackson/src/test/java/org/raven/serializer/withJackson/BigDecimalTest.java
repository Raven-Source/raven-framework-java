package org.raven.serializer.withJackson;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.03.01 23:17
 */
public class BigDecimalTest {

    private JacksonSerializer serializer;

    @Before
    public void init() {
        serializer = new JacksonSerializer();
    }

    @Test
    public void bigDecimalSerializer()
        throws IOException {


        BigDecimal number = new BigDecimal("79228162514264337593543950335");

        System.out.println(serializer.serializeToString(number));

        Order order = new Order();
        order.setId(number);


        System.out.println(serializer.serializeToString(order));
    }

}
