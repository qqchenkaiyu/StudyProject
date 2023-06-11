package com.study.group;

import com.study.TestUtil;
import com.study.group.CollectionTest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupTest {

    @Data
    @AllArgsConstructor
    static class People{
        int age;
        String name;
        String school;
    }
    public static void main(String[] args){
        List<People> peopleList = Arrays.asList(new People(10, "lili", "zhejiang"),
                new People(20, "huohou", "hangzhou"),
                new People(30, "huohou", "zhejiang"),
                new People(40, "huohou", "hangzhou"),
                new People(50, "tt", "hangzhou"));

        TestUtil.executeMethod(()->{
            for (int i = 0; i < 100; i++) {
                peopleList.stream().collect(Collectors.groupingBy(People::getName));
            }
        },"group1");
        TestUtil.executeMethod(()->{
                for (int i = 0; i < 100; i++) {
                     CollectionTest.groupList(peopleList, People::getName, People::getSchool);
                }
            },"group2");

    }
}
