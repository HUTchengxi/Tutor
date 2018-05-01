package org.framework.tutor.annotation;

import java.lang.annotation.*;

/**
 *    
 * @Description 一个账号不允许多个用户同时登录
 * @author yinjimin
 * @date 2018/5/1
 */
//注解放置的位置：type:枚举或者注解上
@Target({ElementType.TYPE, ElementType.METHOD})
//注解的声明周期：source源码时期，class字节码时期，runtime运行时期
@Retention(RetentionPolicy.RUNTIME)
//生成注解文档
@Documented
public @interface OneLogin {
}
