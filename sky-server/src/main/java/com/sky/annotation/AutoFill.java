package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义注解，用于标识需要自动填充的属性
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RUNTIME)
public @interface AutoFill {
    // 数据库操作类型为Updata和Insert
    OperationType value();

}
