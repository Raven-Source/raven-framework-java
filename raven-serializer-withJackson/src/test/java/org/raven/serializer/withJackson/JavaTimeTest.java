package org.raven.serializer.withJackson;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 02:30
 */
public class JavaTimeTest {

    @Test
    public void test() throws Exception {

        JacksonSerializer serializer = new JacksonSerializer();
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

    @Getter
    @Setter
    static class Book {

        private LocalDateTime date;

    }
}
