package com.nanyin.config.operateLog;

import java.lang.annotation.*;

/**
 * Created by NanYin on 2017-07-16 下午11:09.
 * 包名： com.nanyin.common.operateLog
 * 类描述：
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /** 要执行的操作类型比如：add操作 **/
      OperationType operationType() default OperationType.FIND;

    /** 要执行的具体操作比如：添加用户  但必须是在message中的属性名称 如add**/
     String operationName() default "";

     OperateModul operateModul();

     /**
      * 国际化的参数在参数列表中的顺序，从0开始，如{0,1}
      **/
     int[] params() default {};

}
