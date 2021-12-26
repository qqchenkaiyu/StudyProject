package com.study.Spring.ioc;


import cn.hutool.core.util.ClassUtil;
import cn.hutool.setting.Setting;
import com.study.Spring.GPComponent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 根据配置的package进行bean信息的扫描
 */
@Data
public class MyBeanDefinitionReader {

    private Set<Class<?>> registyBeanClasses;

    private Setting config;

    //固定配置文件中的key，相对于xml的规范
    private final String SCAN_PACKAGE = "scanPackage";

    public MyBeanDefinitionReader(String... locations) {
        config = new Setting(locations[0]);
        registyBeanClasses = ClassUtil.scanPackage(config.get(SCAN_PACKAGE));
    }


    //把配置文件中扫描到的所有的配置信息转换为GPBeanDefinition对象，以便于之后IOC操作方便
    public List<MyBeanDefinition> loadBeanDefinitions() {
        List<MyBeanDefinition> result = new ArrayList<>();
        for (Class clazz : registyBeanClasses) {
            if (clazz.isInterface()) {
                continue;
            }
            if (!clazz.isAnnotationPresent(GPComponent.class)) {
                continue;
            }
            result.add(doCreateBeanDefinition(clazz.getSimpleName(), clazz.getName()));
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> i : interfaces) {
                //如果是多个实现类，只能覆盖
                //为什么？因为Spring没那么智能，就是这么傻
                //这个时候，可以自定义名字
                result.add(doCreateBeanDefinition(i.getSimpleName(), clazz.getName()));
            }
        }
        return result;
    }


    //把每一个配信息解析成一个BeanDefinition
    private MyBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        MyBeanDefinition beanDefinition = new MyBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }
}
