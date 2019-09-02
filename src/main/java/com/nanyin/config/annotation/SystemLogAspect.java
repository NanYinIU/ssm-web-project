package com.nanyin.config.annotation;

import com.nanyin.config.locale.LocaleService;
import com.nanyin.config.util.CommonUtil;
import com.nanyin.config.util.HttpsUtil;
import com.nanyin.config.util.MDCUtil;
import com.nanyin.entity.Operate;
import com.nanyin.entity.User;
import com.nanyin.entity.dto.OperateLogDto;
import com.nanyin.services.UserServices;
import com.nanyin.services.impl.OperateServiceImpl;
import org.apache.shiro.session.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * Created by NanYin on 2017-07-16 下午11:18.
 * 应该除了AfterThrowing 抛出异常时应该打印info日志，其他应该打印debug日志
 * 包名： com.nanyin.common.annotation
 * 类描述：
 */

@Aspect
@Component
public class SystemLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    LocaleService localeService;

    @Autowired
    OperateServiceImpl operateService;

    @Autowired
    UserServices userServices;

    private long start;

    @Pointcut("@annotation(com.nanyin.config.annotation.Log)")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作,添加requestId
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void before(JoinPoint joinPoint) {
        try {
            start = System.currentTimeMillis();
            OperateLogDto log = getArgs(joinPoint);
            beforeLog(joinPoint, log);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterThrowing(value = "controllerAspect()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        try {
            OperateLogDto log = getArgs(joinPoint);
            afterLog(joinPoint, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) throws Exception {
        //读取session中的用户;
        HttpServletRequest request = HttpsUtil.getRequest();
        CommonUtil.check(CommonUtil.isNotNull(request), "", "");
        OperateLogDto log = getArgs(joinPoint);
        // 进行对数据的存库操作,先打印debug日志
        debugLog();
        Operate operate = initOperate(request,log);
        afterLog(joinPoint, log);
    }

    private Class<Log> getAnnotationClass() {
        return com.nanyin.config.annotation.Log.class;
    }

    private void debugLog() {

    }

    private Operate initOperate(HttpServletRequest request,OperateLogDto log) throws Exception{
        String name = MDCUtil.getUser();
        String ip = HttpsUtil.getRequestIp(request);
        String os = HttpsUtil.getRequestOs(request);
        User user = userServices.getUserFromUserName(name);
        Operate operate = new Operate();
        operate.setName(log.getOperationName());
        operate.setUser(user);
        operate.setComment("");
        operate.setDevice(os);
        operate.setGmtCreate(new Date());
        operate.setIp(ip);
        operate.setOperateType(log.getOperationType());
        return operateService.save(operate);
    }

    private OperateLogDto getArgs(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        OperationType operationType = OperationType.INIT;
        OperateModul operateModul = OperateModul.OTHER;
        String operationName = "";
        OperateLogDto logDto = new OperateLogDto();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    operationType = method.getAnnotation(getAnnotationClass()).operationType();
                    operationName = method.getAnnotation(getAnnotationClass()).operationName();
                    operateModul = method.getAnnotation(getAnnotationClass()).operateModul();
                    logDto.setOperationType(operationType);
                    logDto.setOperationName(operationName);
                    logDto.setOperateModul(operateModul);
                    break;
                }
            }
        }
        return logDto;
    }

    private void beforeLog(JoinPoint joinPoint, OperateLogDto log) throws ClassNotFoundException {
        long finish = System.currentTimeMillis();
        logger.info("{}", localeService.getMessage("start_log", "",
                joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
                log.getOperateModul(), log.getOperationName(), finish - start));
    }

    private void afterLog(JoinPoint joinPoint, OperateLogDto log) throws ClassNotFoundException {
        long finish = System.currentTimeMillis();
        logger.info("{}", localeService.getMessage("after_log", "",
                joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
                log.getOperateModul(), log.getOperationName(), finish - start));
    }


}
