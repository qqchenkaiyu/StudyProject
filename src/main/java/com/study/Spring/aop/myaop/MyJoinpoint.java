package com.study.Spring.aop.myaop;


import com.study.Spring.aop.gpaop.GPJoinPoint;
import com.study.Spring.aop.gpaop.intercept.GPMethodInterceptor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行切面方法的时候的入参 保存了原来方法的一些信息
 */

@Data
public class MyJoinpoint implements GPJoinPoint {

    private Object proxy;
    private Method method;
    private Object target;
    private Object [] arguments;
    private List<Object> interceptorsAndDynamicMethodMatchers;
    private Class<?> targetClass;
    private HashMap<String, Object> userAttributes;

    public MyJoinpoint(Object[] args, Object target,Method method) {
        arguments=args;
        this.target=target;
        this.method=method;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<String,Object>();
            }
            this.userAttributes.put(key, value);
        }
        else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }


    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }
}
