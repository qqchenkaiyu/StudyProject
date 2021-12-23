package com.study.Spring.aop.myaop2;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调度器  循环执行责任链
 */
public class MyMethodInvocation {

    MyJoinpoint2 joinpoint;
    private List<MyMethodInterceptor> interceptorsAndDynamicMethodMatchers;

    private Map<String, Object> userAttributes;

    //定义一个索引，从-1开始来记录当前拦截器执行的位置
    private int currentInterceptorIndex = -1;

    public MyMethodInvocation(MyJoinpoint2 joinpoint, List<MyMethodInterceptor> advices) {

        this.joinpoint = joinpoint;
        interceptorsAndDynamicMethodMatchers = advices;
    }

    public Object proceed() throws Throwable {
        //如果Interceptor执行完了，则执行joinPoint
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.joinpoint.getMethod().invoke(joinpoint.getTarget(), joinpoint.getArguments());
        }

        MyMethodInterceptor interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        return interceptorOrInterceptionAdvice.invoke(this);

    }

    public Object getThis() {
        return joinpoint.getTarget();
    }

    public Object[] getArguments() {
        return joinpoint.getArguments();
    }

    public Method getMethod() {
        return joinpoint.getMethod();
    }

    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<String, Object>();
            }
            this.userAttributes.put(key, value);
        } else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }


    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }
}
