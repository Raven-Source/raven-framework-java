package org.raven.sping.common;

import org.raven.spring.commons.config.JacksonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author yi.liang
 * date 2021.07.06 11:18
 */
@SpringBootApplication(exclude = {
}, scanBasePackages = "org.raven.sping")
@Import({JacksonConfiguration.class})
public class ApplicationTest {


    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(ApplicationTest.class);
        springApplication.run(args);

    }
}
