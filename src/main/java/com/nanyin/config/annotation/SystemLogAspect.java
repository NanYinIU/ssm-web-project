package com.nanyin.config.annotation;

import com.nanyin.config.locale.LocaleService;
import com.nanyin.config.util.CommonUtil;
import com.nanyin.config.util.HttpsUtil;
import com.nanyin.config.util.MDCUtil;
import org.apache.shiro.session.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by NanYin on 2017-07-16 下午11:18.
 * 包名： com.nanyin.common.annotation
 * 类描述：
 */

@Aspect
@Component
public class SystemLogAspect {

//    //注入Service用于把日志保存数据库
    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    LocaleService localeService;

    //Controller层切点
    @Pointcut("@annotation(com.nanyin.config.annotation.Log)")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作,添加requestId
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void before(JoinPoint joinPoint){
        logger.info("{}",localeService.getMessage("start_log","",this.getClass().getName(),joinPoint.getSignature().getName()));
    }

    @AfterThrowing(value = "controllerAspect()",throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint,Throwable throwable){
        logger.info("error");
        logger.info("{}",localeService.getMessage("after_log","",this.getClass().getName(),joinPoint.getSignature().getName()));
    }


    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        //读取session中的用户;
        HttpServletRequest request = HttpsUtil.getRequest();
        CommonUtil.check(CommonUtil.isNotNull(request),"","");

        Session session = HttpsUtil.getSession();
//        String name = (String) session.getAttribute("username");
        String name = MDCUtil.getUser();
        String ip = HttpsUtil.getRequestIp(request);
        String os = HttpsUtil.getRequestOs(request);
        logger.info("用户名：{} -- ip:{} -- 操作系统：{}",name,ip,os);
        try {
//
//            String targetName = joinPoint.getTarget().getClass().getName();
//            String methodName = joinPoint.getSignature().getName();
//            Object[] arguments = joinPoint.getArgs();
//            Class targetClass = Class.forName(targetName);
//            Method[] methods = targetClass.getMethods();
//            OperationType operationType = OperationType.INIT;
//            OperateModul operateModul = OperateModul.OTHER;
//            String operationName = "";
//            for (Method method : methods) {
//                if (method.getName().equals(methodName)) {
//                    Class[] clazzs = method.getParameterTypes();
//                    if (clazzs.length == arguments.length) {
//                        operationType = method.getAnnotation(getAnnotationClass()).operationType();
//                        operationName = method.getAnnotation(getAnnotationClass()).operationName();
//                        operateModul = method.getAnnotation(getAnnotationClass()).operateModul();
//                        break;
//                    }
//                }
            }catch (Exception e){
                System.out.println("这里有错");
            }
        logger.info("{}",localeService.getMessage("after_log","",this.getClass().getName(),joinPoint.getSignature().getName()));
    }

    private Class<Log> getAnnotationClass(){
        return com.nanyin.config.annotation.Log.class;
    }
}
