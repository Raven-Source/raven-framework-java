package org.raven.example.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {
        ErrorMvcAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
}, scanBasePackages = "org.raven")
@EnableConfigurationProperties
public class ApplicationServiceTest {


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ApplicationServiceTest.class);
        springApplication.run(args);

    }


}
