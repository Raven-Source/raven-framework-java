package org.raven.serializer.withJackson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.raven.serializer.Serializer;

import java.io.IOException;

public class NumberTest {

    private Serializer serializer;

    @Before
    public void init() {
        serializer = new JacksonSerializer();
    }

    @Test
    public void serializer()
            throws IOException {

//        serializerTest("admin-webapi");
        serializerTest(1234);
        serializerTest(12.34);
        serializerTest(12413423232L);
    }

    private void serializerTest(Number value)
            throws IOException {

        byte[] data = serializer.serialize(value);
        String json = new String(data);

        System.out.println("serialize, value:" + json);
        Assert.assertEquals(json, value.toString());

        Number obj = serializer.deserialize(value.getClass(), data);
        Assert.assertEquals(obj, value);
        Assert.assertEquals(json, obj.toString());
        System.out.println("deserialize, obj:" + obj);

    }

}
