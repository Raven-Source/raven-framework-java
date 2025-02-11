package org.raven.serializer.withJackson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.raven.commons.constant.DateFormatString;
import org.raven.serializer.Serializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JacksonSerializerTest {

    User user;

    @Before
    public void before() {

        user = new User();
        user.setId(123);
        user.setName("翻船了");
        user.setList(new ArrayList<Integer>() {
            {
                add(1);
            }

            {
                add(3);
            }
        });
        user.setTime(new Date());

    }

    @Test
    public void Serialize() throws Exception {

        SerializerSetting setting = SerializerSetting.getDefault();
        JacksonSerializer serializer = new JacksonSerializer(setting);

        byte[] data = serializer.serialize(user);
        String json = new String(data, StandardCharsets.UTF_8);

        System.out.println(json);

        SimpleDateFormat formatter = new SimpleDateFormat(DateFormatString.ISO_OFFSET_DATE_TIME);
        formatter.setTimeZone(setting.getTimeZone());
        String serializerRes = "{\"Id\":123,\"Name\":\"翻船了\",\"Time\":\"" + formatter.format(user.getTime()) + "\",\"List\":[1,3],\"A\":0,\"Date2\":null,\"Gender\":1,\"Platform\":\"wx\"}";

        Assert.assertEquals(json, serializerRes);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        serializer.serialize(user, outputStream);

        json = outputStream.toString("UTF-8");
        outputStream.close();
        Assert.assertEquals(json, serializerRes);

        json = serializer.serializeToString(user);
        Assert.assertEquals(json, serializerRes);
    }

    @Test
    public void deserialize() throws Exception {

        SerializerSetting setting = SerializerSetting.getDefault();
        JacksonSerializer serializer = new JacksonSerializer(setting);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        formatter.setTimeZone(setting.getTimeZone());
        String serializerRes = "{\"Id\":123,\"Name\":\"翻船了\",\"Time\":\"" + formatter.format(user.getTime()) + "\",\"List\":[1,3],\"A\":0,\"Date2\":null}";
        byte[] data = serializerRes.getBytes(StandardCharsets.UTF_8);

        User user2 = serializer.deserialize(User.class, data);
        Assert.assertEquals(user.getName(), user2.getName());
        Assert.assertEquals(user.getTime().toString(), user2.getTime().toString());

        user2 = serializer.deserialize(User.class, data, 0, data.length);
        Assert.assertEquals(user.getName(), user2.getName());
        Assert.assertEquals(user.getTime().toString(), user2.getTime().toString());

        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        user2 = serializer.deserialize(User.class, inputStream);
        Assert.assertEquals(user.getName(), user2.getName());
        Assert.assertEquals(user.getTime().toString(), user2.getTime().toString());
        inputStream.close();

        serializerRes = "{\"Id\":123,\"Name\":null,\"List\":[1,3],\"A\":0,\"Gender\":2,\"Date2\":null}";
        data = serializerRes.getBytes(StandardCharsets.UTF_8);
        user2 = serializer.deserialize(User.class, data);
        Assert.assertEquals(user2.getGender(), 2);
        Assert.assertNull(user2.getName());
    }

    @Test
    public void enumTest() throws Exception {

        Paper paper = new Paper();
        paper.setColor(ColorType.C);
        paper.setTitle("人民日报");

        Serializer serializer = new JacksonSerializer();
        byte[] data = serializer.serialize(paper);
        String res = new String(data, "UTF-8");
        System.out.println(res);

        Paper paperDes = serializer.deserialize(Paper.class, data);
        Assert.assertEquals(paper.getColor(), paperDes.getColor());

        String json = "{\"Color\":\"2\"}";   //ColorType:B
        data = json.getBytes("UTF-8");
        paper = serializer.deserialize(Paper.class, data);
        Assert.assertEquals(paper.getColor(), ColorType.B);
        System.out.println(paper.getColor());

         json = "{\"Color\":\"B\"}";   //ColorType:B
        data = json.getBytes("UTF-8");
        paper = serializer.deserialize(Paper.class, data);
        Assert.assertEquals(paper.getColor(), ColorType.B);
        System.out.println(paper.getColor());

        String colorType2Str = new String(serializer.serialize(ColorType2.B));
        System.out.println("Color2: " + colorType2Str);
        Assert.assertEquals(colorType2Str, Integer.toString(ColorType2.B.getValue()));
        ColorType2 colorType2 = serializer.deserialize(ColorType2.class, Integer.toString(ColorType2.B.getValue()).getBytes());
        Assert.assertEquals(colorType2, ColorType2.B);

        json = "{\"desc\":\"abc\", \"Color\":2}";   //ColorType:B
        data = json.getBytes("UTF-8");
        Paper2 paper2 = serializer.deserialize(Paper2.class, data);
        Assert.assertEquals(paper2.getDesc(), "abc");

        paper2 = new Paper2();
        paper2.setColor2(ColorType2.B);
        json = new String(serializer.serialize(paper2), "UTF-8");
        System.out.println("Paper2: " + json);


    }

    @Test
    public void performanceTest() throws Exception {

        Mall mall = new Mall();
        mall.setId(1);
        mall.setDate(new Date());
        mall.setName("大悦城");
        mall.setGroupId(135);
        mall.setAAAAAAAAAA("aaaa");
        mall.setBBBBBBBBBB("BBBB");
        mall.setCCCCCCCCCC("hygfjrt7kuylkhgliu;oi;yhdhtfjhsj");
        mall.setD("kuykj687jrstskhgfk");
        mall.setEEEEEEEEEE("jhlhlgjhkuykjuyt");
        mall.setF("djsgfjdjg");
        mall.setG("fdsgasdgs");
        mall.setHHHHHHHHHH("hgfdhergfdhs");
        mall.setI("fdjnhterjrgtas");
        mall.setJ("fdhs5htrjgfdfdg");

        User user = new User();
        user.setId(132414);
        user.setTime(new Date());
        user.setName("ggsgshahsahsdha");
        mall.setUser(user);

        int seed = 100000;
        Serializer serializer = new JacksonSerializer();
        System.out.printf("序列化数据次数：%d\r\n", seed);

        byte[] data = null;

        data = serializer.serialize(mall);
        mall = serializer.deserialize(Mall.class, data);

        long startTime, endTime;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < seed; i++) {
            data = serializer.serialize(mall);
        }
        endTime = System.currentTimeMillis();
        System.out.printf("Serialize: %dms\r\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < seed; i++) {
            mall = serializer.deserialize(Mall.class, data);
        }
        endTime = System.currentTimeMillis();
        System.out.printf("Deserialize: %dms\r\n", endTime - startTime);

    }

}
