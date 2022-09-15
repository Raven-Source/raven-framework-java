package org.raven.serializer.withJackson;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 02:30
 */
public class JavaTimeTest {

    JacksonSerializer serializer;

    @Before
    public void init() {

        serializer = new JacksonSerializer();
    }

    @Test
    public void test() throws Exception {

        LocalDateTime localDateTime = LocalDateTime.of(2021, 2, 12, 5, 10, 18, 900000000);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("+0800"));

        for (Field declaredField : DateTimeFormatter.class.getDeclaredFields()) {
            if (Modifier.isStatic(declaredField.getModifiers()) && declaredField.getType().equals(DateTimeFormatter.class)) {
                DateTimeFormatter dateTimeFormatter = (DateTimeFormatter) declaredField.get(DateTimeFormatter.class);
                System.out.println(declaredField.getName() + ": "
                        + serializer.getMapper().writeValueAsString(zonedDateTime.format(dateTimeFormatter)));
            }
        }


        String dateStr = localDateTime.toString();
        System.out.println(dateStr);

        String res = serializer.serializeToString(localDateTime);
        System.out.println(res);
        Assert.assertEquals("\"" + dateStr + "\"", res);

        LocalDateTime localDateTime2 = serializer.deserialize(LocalDateTime.class, res.getBytes());
        System.out.println(localDateTime2);
        Assert.assertEquals(localDateTime, localDateTime2);

        Book book = new Book();
        book.setDate(localDateTime2);
        res = serializer.serializeToString(book);
        System.out.println(res);
        Assert.assertEquals(res, "{\"date\":\"" + dateStr + "\"}");

    }

    @Test
    public void localTimeTest() throws Exception {

        LocalTime localTime = LocalTime.now();
        String value = serializer.serializeToString(localTime);
        System.out.println(value);

        localTime = serializer.deserialize(LocalTime.class, value.getBytes());
        System.out.println(localTime);

        localTime = LocalTime.of(8, 10);
        value = serializer.serializeToString(localTime);
        System.out.println(value);

        localTime = serializer.deserialize(LocalTime.class, value.getBytes());
        System.out.println(localTime);

        Order order = new Order();
        order.setLocalTime(localTime);
        order.setLocalTimes(Arrays.asList(LocalTime.now(), localTime));

        value = serializer.serializeToString(order);
        System.out.println(value);


        order = serializer.deserialize(Order.class, "{\"id\":null,\"localTime\":\"08:10:00.000\",\"localTimes\":[\"17:13:00.225\",\"08:10:00.000\"]}".getBytes());

        System.out.println(order.getLocalTime());
        System.out.println(order.getLocalTimes());

    }

    @Getter
    @Setter
    static class Book {

        private LocalDateTime date;

    }
}
