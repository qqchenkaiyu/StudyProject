package com.study.Spring.aop.myaop2;



import com.study.Spring.aop.gpaop.GPAbstractAspectAdvice;
import com.study.Spring.aop.gpaop.GPAdvice;

import java.lang.reflect.Method;

/**
 * 拦截器实现
 */
public class MyMethodBeforeAdviceInterceptor extends GPAbstractAspectAdvice implements GPAdvice, MyMethodInterceptor {


    public MyMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(MyJoinpoint2 joinPoint) throws Throwable{
        super.invokeAdviceMethod(joinPoint,null,null);

    }
    @Override
    public Object invoke(MyMethodInvocation mi) throws Throwable {
        before(mi.joinpoint);
        return mi.proceed();
    }
}
