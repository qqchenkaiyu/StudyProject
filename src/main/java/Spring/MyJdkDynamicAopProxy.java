package com.study.Spring.aop.myaop;


import com.study.Spring.aop.gpaop.GPAopProxy;
import com.study.Spring.aop.gpaop.intercept.GPMethodInvocation;
import com.study.Spring.aspect.LogAspect;
import com.study.Spring.ioc.ApplicaionContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 2019/4/14.
 */
@Data
@AllArgsConstructor
public class MyJdkDynamicAopProxy implements InvocationHandler{

    private MyAdvisedSupport support;
    private ApplicaionContext context;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Map<String,Method> aspectMethods = new HashMap<String,Method>();
        for (Method m : LogAspect.class.getMethods()) {
            aspectMethods.put(m.getName(),m);
        }
        MyJoinpoint myJoinpoint = new MyJoinpoint(args,support.getTarget(),method);
        aspectMethods.get("before").invoke(new LogAspect(),myJoinpoint);
       Object value= method.invoke(support.getTarget(),args);
        aspectMethods.get("after").invoke(new LogAspect(),myJoinpoint);
              return value;
    }
}
