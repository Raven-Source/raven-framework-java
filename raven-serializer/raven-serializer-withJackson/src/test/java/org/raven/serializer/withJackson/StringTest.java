package org.raven.serializer.withJackson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.raven.serializer.Serializer;

import java.io.IOException;

public class StringTest {

    private Serializer serializer;

    @Before
    public void init() {
        serializer = new JacksonSerializer();
    }

    @Test
    public void serializer()
            throws IOException {

//        serializerTest("admin-webapi");
        serializerTest("{\"a\": 1, \"b\": \"2\", \"c\": 3}");
    }

    private void serializerTest(String value)
            throws IOException {

        byte[] data = serializer.serialize(value);
        String json = new String(data);

        System.out.println("serialize, value:" + json);
        System.out.println("serialize, value.getBytes():" + new String(serializer.serialize(value.getBytes())));
        Assert.assertEquals(json, value);

        String obj = serializer.deserialize(String.class, json.getBytes());
        Assert.assertEquals(obj, value);
        Assert.assertEquals(json, obj);
        System.out.println("deserialize, obj:" + obj);

    }

}
