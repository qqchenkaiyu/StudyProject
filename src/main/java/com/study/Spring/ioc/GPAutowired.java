package com.study.Spring.ioc;

import java.lang.annotation.*;


/**
 * 自动注入
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPAutowired {
    String value() default "";
}
