package org.raven.spring.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.raven.spring.commons.config.JacksonConfiguration;
import org.raven.spring.commons.util.JsonUtil;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Slf4j
public class JsonUtilTest {

    @Before
    public void init() throws Exception {

        ObjectMapper objectMapper = new JacksonConfiguration(new JacksonProperties()).objectMapper();

        Method setMapperMethod = JsonUtil.class.getDeclaredMethod("setMapper", ObjectMapper.class);
        setMapperMethod.setAccessible(true);

        Constructor<JsonUtil> constructor = JsonUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        setMapperMethod.invoke(constructor.newInstance(), objectMapper);
    }

    @Test
    public void test()
            throws Exception {
        Activity activity = new Activity();

        log.info(JsonUtil.toJsonString(activity));

        activity = JsonUtil.parseObject("{\"id\":null,\"buId\":null,\"projectId\":null,\"siteId\":null,\"categoryId\":null,\"resIds\":[],\"name\":null,\"timezone\":null,\"startTime\":\"2021-08-03T14:31:54.015+08:00\",\"endTime\":\"2021-08-03T14:31:54.015+08:00\"}"
                , Activity.class);

        log.info(activity.toString());

//        ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
//
//        log.info("------------------------------------");
//
//        log.info(mapper.writeValueAsString(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
//        log.info(mapper.writeValueAsString(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))));

    }

}
