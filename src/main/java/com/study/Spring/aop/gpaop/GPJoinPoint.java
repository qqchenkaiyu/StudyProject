package com.study.Spring.aop.gpaop;

import java.lang.reflect.Method;

/**
 * Created by Tom on 2019/4/15.
 */
public interface GPJoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
