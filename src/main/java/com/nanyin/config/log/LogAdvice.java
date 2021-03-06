package com.nanyin.config.log;

import com.nanyin.config.enums.OperateModuleEnum;
import com.nanyin.config.enums.OperationTypeEnum;
import com.nanyin.services.LocaleService;
import com.nanyin.config.util.CommonUtils;
import com.nanyin.config.util.HttpUtils;
import com.nanyin.config.util.MDCUtil;
import com.nanyin.entity.Operate;
import com.nanyin.entity.User;
import com.nanyin.entity.DTO.OperateLogDto;
import com.nanyin.services.UserServices;
import com.nanyin.services.impl.OperateServiceImpl;
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
 * 包名： com.nanyin.common.log
 * 类描述：
 */

@Aspect
@Component
public class LogAdvice {

    private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);

    @Autowired
    LocaleService localeService;

    @Autowired
    OperateServiceImpl operateService;

    @Autowired
    UserServices userServices;

    private long start;

    @Pointcut("@annotation(com.nanyin.config.log.Log)")
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

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) throws Exception {
        //读取session中的用户;
        HttpServletRequest request = HttpUtils.getHttpServletRequest();
        CommonUtils.check(CommonUtils.isNotNull(request), "", "");
        OperateLogDto log = getArgs(joinPoint);
        // 进行对数据的存库操作,先打印debug日志
        debugLog();
        Operate operate = initOperate(request,log);
        afterLog(joinPoint, log);
    }

    private Class<Log> getAnnotationClass() {
        return com.nanyin.config.log.Log.class;
    }

    private void debugLog() {

    }

    private Operate initOperate(HttpServletRequest request,OperateLogDto log) throws Exception{
        String name = MDCUtil.getUser();
        String ip = HttpUtils.getRequestIp(request);
        String os = HttpUtils.getRequestOs(request);
        User user = userServices.getUserFromUserName(name);
        Operate operate = new Operate();
        operate.setName(log.getOperationName());
        operate.setUser(user);
        operate.setComment("");
        operate.setDevice(os);
        operate.setGmtCreate(new Date());
        operate.setIp(ip);
        operate.setOperateType(log.getOperationTypeEnum());
        return operateService.save(operate);
    }

    private OperateLogDto getArgs(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        OperationTypeEnum operationTypeEnum = OperationTypeEnum.INIT;
        OperateModuleEnum operateModuleEnum = OperateModuleEnum.OTHER;
        String operationName = "";
        OperateLogDto logDto = new OperateLogDto();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    int[] param = method.getAnnotation(getAnnotationClass()).params();
                    operationTypeEnum = method.getAnnotation(getAnnotationClass()).operationType();
                    operationName = localeService.getMessage(method.getAnnotation(getAnnotationClass()).operationName(),
                            getArguments(arguments,param));
                    operateModuleEnum = method.getAnnotation(getAnnotationClass()).operateModul();
                    logDto.setOperationTypeEnum(operationTypeEnum);
                    logDto.setOperationName(operationName);
                    logDto.setOperateModuleEnum(operateModuleEnum);
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
                log.getOperateModuleEnum(), log.getOperationName(), finish - start));
    }

    private void afterLog(JoinPoint joinPoint, OperateLogDto log) throws ClassNotFoundException {
        long finish = System.currentTimeMillis();
        logger.info("{}", localeService.getMessage("after_log", "",
                joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
                log.getOperateModuleEnum(), log.getOperationName(), finish - start));
    }

    /**
     * 获取方法上的参数 arguments指的是所有的方法上的参数， param指的是需要进行国际化参数的参数顺序
     **/
    private Object[] getArguments(Object[] arguments,int[] param){
        Object[] args = new Object[param.length];
        for (int order:param
        ) {
            Object temp = arguments[order];
            args[order] = temp;
        }
        return args;
    }

}
