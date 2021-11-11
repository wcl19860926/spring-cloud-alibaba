package com.study.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.study.dubbo.api.service.IHelloService;
import org.springframework.beans.factory.annotation.Value;

@Service
public class HelloService implements IHelloService {

    @Value("${dubbo.application.name}")
    private   String   serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s]:Hello, %s", serviceName, name);
    }
}
