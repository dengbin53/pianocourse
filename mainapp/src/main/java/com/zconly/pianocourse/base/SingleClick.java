package com.zconly.pianocourse.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 防连点
 * @Author: dengbin
 * @CreateDate: 2020/3/30 15:39
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 15:39
 * @UpdateRemark: 更新说明
 */
@Retention(RetentionPolicy.RUNTIME) // 注解保留至运行时
@Target(ElementType.METHOD) // 声明注解作用在方法上面
public @interface SingleClick {
    long value() default 1024;
}
