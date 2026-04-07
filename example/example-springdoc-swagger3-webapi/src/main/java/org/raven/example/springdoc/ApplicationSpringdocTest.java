package org.raven.example.springdoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {
}, scanBasePackages = "org.raven")
@EnableConfigurationProperties
public class ApplicationSpringdocTest {


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ApplicationSpringdocTest.class);
        springApplication.run(args);

    }


}
