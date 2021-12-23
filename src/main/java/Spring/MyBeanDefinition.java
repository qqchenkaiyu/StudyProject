package com.study.Spring.ioc;

import lombok.Data;

/**
 * bean信息描述文件 .
 */
@Data
public class MyBeanDefinition {
    private String beanClassName;
    private String factoryBeanName;
    private boolean isSingleton = true;
}
