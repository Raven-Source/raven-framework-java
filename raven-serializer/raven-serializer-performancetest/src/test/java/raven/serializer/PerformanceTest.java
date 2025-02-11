package raven.serializer;

import org.junit.Test;
import org.raven.serializer.Serializer;
import org.raven.serializer.SerializerFactory;
import org.raven.serializer.SerializerType;
import org.raven.serializer.withJackson.ObjectMapperConfig;
import org.raven.serializer.withJackson.SerializerSetting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class PerformanceTest {

    @Test
    public void testAll()
        throws Exception {

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

        Serializer serializer = SerializerFactory.create(SerializerType.Jackson, new Object[]{SerializerSetting.getDefault()});
        byte[] data = serializer.serialize(mall);
        String jsonStr = new String(data, StandardCharsets.UTF_8);

        System.out.println(jsonStr);

        serializer = SerializerFactory.create(SerializerType.Jackson, new Object[]{ObjectMapperConfig.getObjectMapper()});
        data = serializer.serialize(mall);
        jsonStr = new String(data, StandardCharsets.UTF_8);

        System.out.println(jsonStr);

    }

    @Test
    public void performanceTest() throws Exception {

        int seed = 1000;

        System.out.printf("序列化数据次数：%d\r\n", seed);
        for (SerializerType type : SerializerType.values()) {

            test(seed, type);
        }


    }

    private void test(int seed, SerializerType serializerType) throws IOException {

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

        byte[] data = null;

        Serializer serializer = SerializerFactory.create(serializerType);
        System.out.printf("SerializerType：%s\r\n", serializerType.toString());
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
