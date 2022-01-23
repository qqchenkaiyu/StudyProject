package com.study.copyClassTest.Test;

import com.study.copyClassTest.pojo.Boy;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2019/4/13.
 */
public class TestCopy {
    public void testSetter(int times, Boy boy) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Boy boy1 = new Boy();
            boy1.SettetBoy(boy);
        }
        System.out.println("testSetter useTime " + (System.currentTimeMillis() - begin));
    }

    public void testFuzhi(int times, Boy boy) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Boy boy1 = new Boy();
            boy1.FuzhiBoy(boy);
        }
        System.out.println(" testFuzhi useTime " + (System.currentTimeMillis() - begin));
    }

    public void testSpringCopy(int times, Boy boy) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Boy boy1 = new Boy();
            boy1.SpringBeanBoy(boy);
        }
        System.out.println(" testSpringCopy useTime " + (System.currentTimeMillis() - begin));
    }

    public void testCommonCopy(int times, Boy boy) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Boy boy1 = (Boy) BeanUtils.cloneBean(boy);
        }
        System.out.println(" testCommonCopy useTime  " + (System.currentTimeMillis() - begin));
    }

    public void testSystemClone(int times, Boy boy) throws Exception {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Boy boy1 = (Boy) boy.clone();
        }
        System.out.println(" testSystemClone useTime " + (System.currentTimeMillis() - begin));
    }

    public void testMyReflect(int times, Object boy) throws Exception {
        Class<?> boyClass = boy.getClass();
        Field[] fields = boyClass.getDeclaredFields();
        Object[] objects = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].isAccessible()) fields[i].setAccessible(true);
            objects[i] = fields[i].get(boy);
        }
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Object boy1 = boyClass.newInstance();
            for (int j = 0; j < fields.length; j++) {
                fields[j].set(boy1, objects[j]);
            }
        }
        System.out.println(" testMyReflect useTime " + (System.currentTimeMillis() - begin));
    }

    public void testCGLIB(int times, Object boy) throws Exception {
        BeanCopier beanCopier = BeanCopier.create(boy.getClass(), boy.getClass(), false);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            Boy boy1 = new Boy();
            boy1.CglibBoy(boy, beanCopier);
            //System.out.println(boy1);
        }
        System.out.println(" testCGLIB useTime " + (System.currentTimeMillis() - begin));
    }
}
