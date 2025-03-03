package org.raven.sping.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.raven.commons.contract.ResponseModel;
import org.raven.serializer.withJackson.JsonUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Arrays;

/**
 * date 2022/8/9 16:53
 */
@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
@Slf4j
public class JsonUtilTest {

    @Test
    public void test()
            throws Exception {

        LocalTime localTime = LocalTime.of(8, 10);
        Order order = new Order();
        order.setLocalTime(localTime);
        order.setLocalTimes(Arrays.asList(LocalTime.now(), localTime));

        String value = JsonUtils.toJsonString(order);
        System.out.println(value);


        order = JsonUtils.parseObject("{\"id\":null,\"localTime\":\"08:10:00.000\",\"localTimes\":[\"17:13:00.225\",\"08:10:00.000\"]}"
                , Order.class);

        System.out.println(order.getLocalTime());
        System.out.println(order.getLocalTimes());

        ResponseModel<Order> res = new ResponseModel<>(true, order, "操作成功", "5");
        System.out.println(JsonUtils.toJsonString(res));

        res = JsonUtils.parseObject("{\"code\":\"5\",\"data\":{\"id\":null,\"localTime\":\"08:10:00.000\",\"localTimes\":[\"17:13:00.225\",\"08:10:00.000\"]},\"success\":true,\"extension\":{},\"msg\":\"操作成功\"}"
                , ResponseModel.class, Order.class);


//        ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
//
//        log.info("------------------------------------");
//
//        log.info(mapper.writeValueAsString(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
//        log.info(mapper.writeValueAsString(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))));

    }

}