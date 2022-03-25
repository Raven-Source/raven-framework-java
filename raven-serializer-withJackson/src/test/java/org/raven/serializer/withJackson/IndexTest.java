package org.raven.serializer.withJackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.raven.commons.data.MemberFormatType;
import org.raven.commons.data.annotation.Contract;
import org.raven.commons.data.annotation.Ignore;
import org.raven.commons.data.annotation.Member;
import org.raven.serializer.Serializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.08.24 01:15
 */
public class IndexTest {


    private Serializer serializer;

    @Before
    public void init() {
        serializer = new JacksonSerializer();
    }

    @Test
    public void ignoreTest()
        throws IOException {

        IgnoreModel ignoreModel = new IgnoreModel()
            .setId(234)
            .setName("hi")
            .setList(Arrays.asList(1, 2, 3, 4));


        byte[] data = serializer.serialize(ignoreModel);
        String value = new String(data);
        System.out.println("serialize, value:" + value);

        Assert.assertEquals(value, "{\"Name\":\"hi\",\"List\":[1,2,3,4],\"Id\":234}");


        IgnoreModel2 ignoreModel2 = new IgnoreModel2()
            .setId(234)
            .setName("hi")
            .setList(Arrays.asList(1, 2, 3, 4));


        data = serializer.serialize(ignoreModel);
        value = new String(data);
        System.out.println("serialize, value:" + value);

        Assert.assertEquals(value, "{\"Name\":\"hi\",\"List\":[1,2,3,4],\"Id\":234}");

    }


    @Contract(formatType = MemberFormatType.PascalCase)
    @Accessors(chain = true)
    @Data
    public static class IgnoreModel {

        @Member(index = 3)
        private int id;

        @Member(index = 1)
        private String name;

        @Member(index = 2)
        private List<Integer> list;
    }


    @Contract(formatType = MemberFormatType.PascalCase)
    @Accessors(chain = true)
    @Data
    public static class IgnoreModel2 {

        @JsonProperty(index = 3)
        private int id;

        @JsonProperty(index = 1)
        private String name;

        @JsonProperty(index = 2)
        private List<Integer> list;
    }
}
