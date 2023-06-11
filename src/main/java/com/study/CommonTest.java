package com.study;

import ch.qos.logback.core.util.TimeUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.SystemUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CommonTest {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class People {
        int age;
        String name;
        String school;
        People2 people2;
    }

    @Data
    @AllArgsConstructor
    static class People2 {
        int age;
        String name;
        String school;
        People3 people3;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class People3 {
        int age;
        String name;
        String school;
    }

    public static void main(String[] args) {
        CompletableFuture<Void> async = CompletableFuture.runAsync(() -> {
            ThreadUtil.safeSleep(1000);
            System.out.println("睡眠1秒");
        });
        CompletableFuture<Void> async2 = CompletableFuture.runAsync(() -> {
            ThreadUtil.safeSleep(2000);
            System.out.println("睡眠2秒");
        });
        CompletableFuture.allOf(async, async2).thenRun(() -> System.out.println("all success")).join();

        // 连续空指针处理
        People people = new People();
        People3 people3 = Optional.of(people)
                .map(People::getPeople2).map(People2::getPeople3).orElse(
                        new People3());
        System.out.println(people3);
    }
}
