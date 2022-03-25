package org.raven.commons.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.raven.commons.constant.DateFormatStringConstant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateTimeUtilsTest {


    @Test
    public void test()
            throws Exception {

        Date now = new Date();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        for (Field declaredField : DateTimeFormatter.class.getDeclaredFields()) {
            if (Modifier.isStatic(declaredField.getModifiers()) && declaredField.getType().equals(DateTimeFormatter.class)) {
                DateTimeFormatter dateTimeFormatter = (DateTimeFormatter) declaredField.get(DateTimeFormatter.class);

                log.info(String.format("%s: %s"
                        , declaredField.getName()
                        , zonedDateTime.format(dateTimeFormatter))
                );
            }
        }

        for (Field declaredField : DateFormatStringConstant.class.getDeclaredFields()) {
            if (Modifier.isStatic(declaredField.getModifiers())) {

                String formatStr = (String) declaredField.get(DateFormatStringConstant.class);

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatStr);

                log.info(String.format("%s: %s"
                        , formatStr
                        , zonedDateTime.format(dateTimeFormatter))
                );
            }
        }

    }

}
