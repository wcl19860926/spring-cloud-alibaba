package com.study.dubbo.controller;

import com.study.dubbo.api.service.IHelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Reference(url= "dubbo://192.168.2.147:20880/com.study.dubbo.api.service.IHelloService")
    private IHelloService  iHelloService;

    @GetMapping("/say")
    public  String sayHello(){
        return  iHelloService.sayHello("Mic");
    }
}
