package com.study.service;


import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
public class HelloServiceImpl implements IHelloService {

    @Value("${dubbo.application.name}")
    private String application;


    @Override
    public String sayHello(String name) {
        return String.format("[%s]:hello ,%s", application, name);
    }
}
