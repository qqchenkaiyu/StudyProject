package com.study.Spring.ioc;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * bean信息描述文件 .
 */
@Data
public class MyBeanDefinition {
    private String beanClassName;
    private String factoryBeanName;
    private Set<String> factoryBeanNames = new HashSet<>();
    private boolean isSingleton = true;
}
