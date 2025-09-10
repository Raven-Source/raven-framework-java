package org.raven.logger.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties
@Slf4j
public class ApplicationTest {

    public static volatile boolean isDead = false;

    public static void main(String[] args) throws InterruptedException {

        SpringApplication springApplication = new SpringApplication(ApplicationTest.class);
        springApplication.run(args);

        while (!isDead){
            Thread.sleep(4000);
        }
    }

}
