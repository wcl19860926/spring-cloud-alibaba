package com.study.nacos.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class NacosDemo001Application {

    public static void main(String[] args) {
            ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosDemo001Application.class, args);
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            System.err.println("user name :"+userName+"; age: "+userAge);

    }
}
