package com.study.Spring.aop.myaop2;

import com.study.Spring.aop.gpaop.intercept.GPMethodInvocation;

/**
 * 拦截器的接口
 */
public interface MyMethodInterceptor {
    Object invoke(MyMethodInvocation invocation) throws Throwable;
}
