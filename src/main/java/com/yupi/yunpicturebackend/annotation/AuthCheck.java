package com.yupi.yunpicturebackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加注解类，使用AOP切面的方法去管理用户权
 * 下面的是注解是设定注解的生效范围如在方法中生效以及生效时间如运行时生效
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//自动扫描的注解，可以实现统一的拦截，和权限校验
public @interface AuthCheck {
    /**
     * 必须具有某个角色，如必须登录才能调用接口
     * @return
     */
    String mustRole() default "";
}
