package org.raven.serializer.withJackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.raven.commons.util.Sets;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.raven.commons.constant.DateFormatString.*;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.06.28 16:31
 */
public class DateTimeTest {

    @Test
    public void test() throws IOException, IllegalAccessException {

        SerializerSetting setting = SerializerSetting.getDefault();
        String[] deserializeDateFormatString = setting.getDeserializeDateFormatString();

        Set<String> dateFormatStringSet = Sets.newHashSet(deserializeDateFormatString);
        dateFormatStringSet.add(NON_ISO_LOCAL_DATE_TIME);
        dateFormatStringSet.add(NON_ISO_LOCAL_DATE_TIME_SECOND);

        setting.setDeserializeDateFormatString(dateFormatStringSet.toArray(new String[0]));

        JacksonSerializer serializer = new JacksonSerializer(setting);

        User user = new User();
        user.setDate2(new Date());

        String res = serializer.serializeToString(user);
        System.out.println(res);

        res = "{\"Id\":0,\"Name\":null,\"Time\":null,\"List\":null,\"A\":0,\"Date2\":\"2019-06-28 16:34:46\",\"Gender\":1}";
        user = serializer.deserialize(User.class, res.getBytes());
        System.out.println(user.toString());


        res = "{\"Id\":0,\"Name\":null,\"Time\":null,\"List\":null,\"A\":0,\"Date2\":\"2019-06-28T20:10:11.907+0800\",\"Gender\":2}";
        user = serializer.deserialize(User.class, res.getBytes());
        System.out.println(user.toString());

        res = "{\"Id\":0,\"Name\":null,\"Time\":null,\"List\":null,\"A\":0,\"Date2\":\"2019-06-28 16:34:46.123\",\"Gender\":1}";
        user = serializer.deserialize(User.class, res.getBytes());
        System.out.println(user.toString());

        ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();

        System.out.println("------------------------------------");

        System.out.println(mapper.writeValueAsString(ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
        System.out.println(mapper.writeValueAsString(ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))));
        System.out.println(mapper.writeValueAsString(ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
        System.out.println(mapper.writeValueAsString(ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))));

        for (Field declaredField : DateTimeFormatter.class.getDeclaredFields()) {
            if (Modifier.isStatic(declaredField.getModifiers()) && declaredField.getType().equals(DateTimeFormatter.class)) {
                DateTimeFormatter dateTimeFormatter = (DateTimeFormatter) declaredField.get(DateTimeFormatter.class);

                System.out.println(String.format("%s: %s"
                                , declaredField.getName()
//                    , dateTimeFormatter.toString()
                                , mapper.writeValueAsString(ZonedDateTime.now().format(dateTimeFormatter)))
                );
            }
        }

    }

}
