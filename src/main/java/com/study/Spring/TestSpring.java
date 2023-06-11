package com.study.Spring;

import com.study.Spring.domain.PersonInterface;
import com.study.Spring.ioc.ApplicaionContext;
import com.study.Spring.domain.Person;

public class TestSpring {
    public static void main(String[] args) {
        ApplicaionContext context=new ApplicaionContext("beans.setting");
        PersonInterface person=context.getBean(Person.class);
        person.hello();
    }
    private static void testSpring() {

    }
}
