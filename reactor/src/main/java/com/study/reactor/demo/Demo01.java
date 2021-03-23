package com.study.reactor.demo;

import reactor.core.publisher.Flux;

public class Demo01 {

    public static void main(String[] args) {
        Flux.just("A","B","C").subscribe(item-> System.out.println(item));
        Flux.create(fluxSink -> {
            fluxSink.
            fluxSink.complete();


        }).subscribe(System.out::println);
    }
}
