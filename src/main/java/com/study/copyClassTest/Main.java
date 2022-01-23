package com.study.copyClassTest;

import com.study.copyClassTest.Test.TestCopy;
import com.study.copyClassTest.pojo.Boy;
import com.study.copyClassTest.pojo.Girl;


import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        Boy boy = new Boy();
        boy.setName("davy");
        boy.setWeight("80kg");
        boy.setHeight("175");
        boy.setAge(18);

        Girl girl = new Girl();
        girl.setName("lili");
        girl.setWeight("40kg");
        girl.setHeight("165");
        girl.setAge(18);
        boy.setGirl(girl);


        TestCopy testCopy = new TestCopy();
        int Times=10000*1000;
        testCopy.testSetter(Times,boy);
        testCopy.testFuzhi(Times,boy);
        testCopy.testSpringCopy(Times,boy);



        try {
            testCopy.testMyReflect(Times,boy);
            testCopy.testCGLIB(Times,boy);
            testCopy.testSystemClone(Times,boy);
            testCopy.testCommonCopy(Times,boy);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
