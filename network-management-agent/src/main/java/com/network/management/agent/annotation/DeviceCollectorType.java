package com.network.management.agent.annotation;

import java.lang.annotation.*;

/**
 * 设备类型注解
 * @author yyc
 * @date 2020/9/12 22:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DeviceCollectorType {
    String value();
}
