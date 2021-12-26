package com.study.Spring.aop.myaop2;


import com.study.Spring.aop.gpaop.GPAfterReturningAdviceInterceptor;
import com.study.Spring.aop.gpaop.GPMethodBeforeAdviceInterceptor;
import com.study.Spring.aop.myaop.MyAdvisedSupport;
import com.study.Spring.aspect.LogAspect;
import com.study.Spring.ioc.ApplicaionContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 2019/4/14.
 */
@Data
@AllArgsConstructor
public class MyJdkDynamicAopProxy2 implements InvocationHandler{

    private MyAdvisedSupport support;
    private ApplicaionContext context;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //造一个责任链一直运行
        Map<String,Method> aspectMethods = new HashMap<String,Method>();
        for (Method m : LogAspect.class.getMethods()) {
            aspectMethods.put(m.getName(),m);
        }
            List<MyMethodInterceptor> advices = new LinkedList<>();
            advices.add(new MyMethodBeforeAdviceInterceptor(aspectMethods.get("before"),new LogAspect()));
            advices.add(new MyAfterReturningAdviceInterceptor(aspectMethods.get("after"),new LogAspect()));

        MyJoinpoint2 myJoinpoint = new MyJoinpoint2(args,support.getTarget(),method);
        MyMethodInvocation invocation = new MyMethodInvocation(myJoinpoint,advices);
        return invocation.proceed();
    }
}
