package com.study.Spring.aop.gpaop;


import com.study.Spring.aop.gpaop.intercept.GPMethodInvocation;
import com.study.Spring.aop.gpaop.support.GPAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by Tom on 2019/4/14.
 */
public class GPJdkDynamicAopProxy implements  GPAopProxy,InvocationHandler{

    private GPAdvisedSupport advised;

    public GPJdkDynamicAopProxy(GPAdvisedSupport config){
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,this.advised.getTargetClass());
        GPMethodInvocation invocation = new GPMethodInvocation(proxy,this.advised.getTarget(),method,args,this.advised.getTargetClass(),interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }
}
