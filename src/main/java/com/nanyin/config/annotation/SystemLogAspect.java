package com.nanyin.config.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;


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

    //Controller层切点
    @Pointcut("@annotation(com.nanyin.config.annotation.Log)")
    public  void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作,添加requestId
     *
     * @param joinPoint 切点
     */
    //配置controller环绕通知,使用在方法aspect()上注册的切入点
    @Before("controllerAspect()")
    public void around(JoinPoint joinPoint){
        logger.info("==========开始执行controller前置通知===============");
        long start = System.currentTimeMillis();
        try {
            ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            logger.info("==========结束执行controller前置通知===============");
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
        }
    }

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        //读取session中的用户;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("user");
        String ip = request.getRemoteAddr();

        try {

            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            LogType operationType = LogType.OTHER_MODULT;
            String operationName = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationType = method.getAnnotation(com.nanyin.config.annotation.Log.class).operationType();
                        operationName = method.getAnnotation(com.nanyin.config.annotation.Log.class).operationName();
                        break;
                    }
                }
            }
            //*========控制台输出=========*//
            System.out.println("=====controller后置通知开始=====");
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType.toString());
            System.out.println("方法描述:" + operationName);
            System.out.println("请求人:" + name);
            System.out.println("请求IP:" + ip);
            //*========数据库日志=========*//
//            Log log = new Log();
////            随机的识别码id
//            log.setId(UUID.randomUUID().toString());
//            log.setContent(operationName+(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);
//            log.setLogType("0");
//            log.setIp(ip);
//            log.setType_id("null");
////            log.setUser(name);
//            log.setCreateDate(new Date());
//            System.out.println(log.toString());
            //保存数据库
            try {
//                logService.insert(log);
            }catch (Exception e){
                System.out.println("这里有错");
            }

            System.out.println("=====controller后置通知结束=====");
        }  catch (Exception e) {
            //记录本地异常日志
            logger.error("==后置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }


}
