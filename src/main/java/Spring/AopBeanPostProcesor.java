package com.study.Spring;

import cn.hutool.setting.Setting;
import com.study.Spring.aop.config.GPAopConfig;
import com.study.Spring.aop.myaop.MyAdvisedSupport;
import com.study.Spring.aop.myaop2.MyJdkDynamicAopProxy2;
import com.study.Spring.ioc.ApplicaionContext;
import com.study.Spring.ioc.GPAutowired;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Proxy;

@GPComponent
@Data
public class AopBeanPostProcesor {


    ApplicaionContext context;

    public Object postAfterInitial(Object instance) {
        MyAdvisedSupport support = getSupport();
        //符合PointCut的规则的话，创建代理对象
        support.setTarget(instance);
        if (support.pointCutMatch(instance.getClass())) {
            instance = Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(), new MyJdkDynamicAopProxy2(support, context));
        }
        return instance;
    }

    private MyAdvisedSupport getSupport() {
        GPAopConfig config = new GPAopConfig();
        Setting config1 = context.getReader().getConfig();
        config.setPointCut(config1.get("pointCut"));
        config.setAspectClass(config1.get("aspectClass"));
        config.setAspectBefore(config1.get("aspectBefore"));
        config.setAspectAfter(config1.get("aspectAfter"));
        config.setAspectAfterThrow(config1.get("aspectAfterThrow"));
        config.setAspectAfterThrowingName(config1.get("aspectAfterThrowingName"));
        return new MyAdvisedSupport(config);
    }

}
