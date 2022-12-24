package com.newcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* 只起标记作用
* 打上这个登录才能访问
* */
@Target(ElementType.METHOD) // 注解写在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface LoginRequired {

}
