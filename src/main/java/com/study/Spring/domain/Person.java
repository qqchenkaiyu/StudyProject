package com.study.Spring.domain;

import com.study.Spring.GPComponent;

@GPComponent
public class Person implements PersonInterface{
    private String name;
    private Integer age;

    public void hello(){
        System.out.println("hello");
    }
}
