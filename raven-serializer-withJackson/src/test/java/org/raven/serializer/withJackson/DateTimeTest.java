package org.raven.serializer.withJackson;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.06.28 16:31
 */
public class DateTimeTest {

    @Test
    public void test() throws IOException {


        JacksonSerializer serializer = new JacksonSerializer();

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

    }

}
