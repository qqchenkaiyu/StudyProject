package com.study.Spring.aop.myaop;


import com.study.Spring.aop.gpaop.GPAfterReturningAdviceInterceptor;
import com.study.Spring.aop.gpaop.GPAfterThrowingAdviceInterceptor;
import com.study.Spring.aop.gpaop.GPMethodBeforeAdviceInterceptor;
import com.study.Spring.aop.config.GPAopConfig;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据全局aop设置 和 当前在实例化的类生成  可以判断是否需要切面以及预先生成advise
 */
@Data
public class MyAdvisedSupport {

    private Class<?> targetClass;

    private Object target;

    private GPAopConfig config;

    private Pattern pointCutClassPattern;

    private transient Map<Method, List<Object>> methodCache;

    public MyAdvisedSupport(GPAopConfig config) {
        this.config = config;
    }

    public Class<?> getTargetClass(){
        return this.targetClass;
    }

    public Object getTarget(){
        return this.target;
    }



    public boolean pointCutMatch(Class<?> aClass) {
        String pointCut = config.getPointCut()
                .replaceAll("\\.","\\\\.")
                .replaceAll("\\\\.\\*",".*")
                .replaceAll("\\(","\\\\(")
                .replaceAll("\\)","\\\\)");
        //pointCut=public .* com.gupaoedu.vip.spring.demo.service..*Service..*(.*)
        //玩正则
        String pointCutForClassRegex = pointCut.substring(0,pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ") + 1));
        return pointCutClassPattern.matcher(aClass.toString()).matches();
    }

}
