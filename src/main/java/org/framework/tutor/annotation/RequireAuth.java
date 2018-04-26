/*
 * Copyright (C) 2011-2013 ShenZhen iBoxpay Information Technology Co. Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary information of iBoxPay Company of China.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement you entered into with iBoxpay inc.
 *
 *
 */
package org.framework.tutor.annotation;

import java.lang.annotation.*;

/**
 * @author yinjimin
 * @Description: 对应的url访问对应的角色名
 * @date 2018年04月26日
 */
//该注解表示：如果定义在类上，那么会对应的子类也会有该注解修饰
@Inherited
//注解放置的位置：type:枚举或者注解上
@Target({ElementType.TYPE, ElementType.METHOD})
//注解的声明周期：source源码时期，class字节码时期，runtime运行时期
@Retention(RetentionPolicy.RUNTIME)
//生成注解文档
@Documented
public @interface RequireAuth {
    String ident() default "";
}
