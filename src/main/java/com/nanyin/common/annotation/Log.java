package com.nanyin.common.annotation;

import java.lang.annotation.*;

/**
 * Created by NanYin on 2017-07-16 下午11:09.
 * 包名： com.nanyin.common.annotation
 * 类描述：
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /** 要执行的操作类型比如：add操作 **/
      String operationType() default "";

    /** 要执行的具体操作比如：添加用户 **/
     String operationName() default "";

}
