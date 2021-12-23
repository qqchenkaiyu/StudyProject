package com.study.Spring.aop.gpaop;

/**
 * Created by Tom.
 */
public interface GPAopProxy {


    Object getProxy();


    Object getProxy(ClassLoader classLoader);
}
