package com.study.Spring.aop.myaop2;


import com.study.Spring.aop.gpaop.GPAbstractAspectAdvice;
import com.study.Spring.aop.gpaop.GPAdvice;
import com.study.Spring.aop.gpaop.GPJoinPoint;
import com.study.Spring.aop.gpaop.intercept.GPMethodInterceptor;
import com.study.Spring.aop.gpaop.intercept.GPMethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by Tom on 2019/4/15.
 */
public class MyAfterReturningAdviceInterceptor extends GPAbstractAspectAdvice implements GPAdvice, MyMethodInterceptor {

    private GPJoinPoint joinPoint;

    public MyAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MyMethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        joinPoint=mi.joinpoint;
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint,retVal,null);
    }


}
