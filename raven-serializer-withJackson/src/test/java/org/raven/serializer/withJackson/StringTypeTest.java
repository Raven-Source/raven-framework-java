package org.raven.serializer.withJackson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.raven.serializer.Serializer;

import java.io.IOException;

public class StringTypeTest {

    private Serializer serializer;

    @Before
    public void init() {
        serializer = new JacksonSerializer();
    }

    @Test
    public void valueEnumSerializer()
            throws IOException {

        serializerTest(Platform.Ali);
        serializerTest(Platform.WX);

        serializerTest(Platform.Ali);
        serializerTest(Platform.WX);
    }

    private void serializerTest(Platform platform)
            throws IOException {

        byte[] data = serializer.serialize(platform);
        String value = new String(data);

        Assert.assertEquals(value, "\"" + platform.getValue() + "\"");
        System.out.println("serialize, value:" + value);

        platform = serializer.deserialize(Platform.class, value.getBytes());
        Assert.assertEquals(value, "\"" + platform.getValue() + "\"");
        System.out.println("deserialize, value:" + platform);

    }

}
