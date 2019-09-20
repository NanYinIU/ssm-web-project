package com.nanyin.config.quartz.Job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NanYin on 2019/9/18.
 * 调用jar包或者调用全类名中的main方法
 * 具体参数格式:
 * 1. jar包方式:
 *      java [vmParam] -jar xxx.jar [a b c]
 * 2. java类的方式
 *
 */
@DisallowConcurrentExecution
public class DefaultJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(DefaultJob.class);

    private final static String TASK = "com.nanyin.config.quartz.task.Task";

    private final static String TASK_METHOD = "execute";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String jarPath = map.getString("jarPath");
        String parameter = map.getString("parameter");
        String vmParam = map.getString("vmParam");
        String fullClassName = map.getString("jobClass");
        logger.info("Running Job name : {} ", map.getString("name"));
        logger.info("Running Job description : " + map.getString("JobDescription"));
        logger.info("Running Job group: {} ", map.getString("group"));
        logger.info("Running Job cron : " + map.getString("cronExpression"));
        logger.info("Running Job parameter : {} ", parameter);
        logger.info("Running Job vmParam : {} ", vmParam);
        long startTime = System.currentTimeMillis();
        if (jarPath != null && !"".equals(jarPath)) {
            logger.info("Running Job jar path : {} ", jarPath);
            File jar = new File(jarPath);
            if (jar.exists()) {
                // 如果jar的路径存在
                // 创建一个系统进程处理jar
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.directory(jar.getParentFile());
                List<String> commands = new ArrayList<>();
                commands.add("java");
                if (vmParam != null && !"".equals(vmParam)) {
                    commands.add(vmParam);
                }
                commands.add("-jar");
                commands.add(jarPath);
                if(parameter !=null && !"".equals(parameter)){
                    commands.add(parameter);
                }
                processBuilder.command(commands);
                logger.info("Running Job details as follows >>>>>>>>>>>>>>>>>>>>: ");
                logger.info("Running Job commands : {}  ", commands);
                try {
                    Process process = processBuilder.start();
                } catch (IOException e) {
                    throw new JobExecutionException(e);
                }
            }
        } else if (fullClassName != null && !"".equals(fullClassName)) {
            // 通过全类名 进行反射的调用main方法
            logger.info("Running Job class path : {} ",fullClassName);
            try {
                Class<?> clazz = Class.forName(fullClassName);
                if(judgeExecute(clazz)){
                    // 执行Task接口方法中的excute方法
                    Method main = clazz.getDeclaredMethod(TASK_METHOD, String[].class);
                    String[] args = new String[]{};
                    if(parameter!=null && !"".equals(parameter)){
                        args = parameter.split(" ");
                    }
                    Object o = clazz.newInstance();
                    // 这里的args虽然是数组，但是需要转换为object类对象，否则会报错：
                    // Caused by: java.lang.IllegalArgumentException: wrong number of arguments
                    main.invoke(o,(Object) args);
                }
            } catch (ClassNotFoundException e) {
                logger.info( "class {} is not exsit! ");
            } catch (NoSuchMethodException e) {
                logger.info("no main method!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否包含task接口
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     */
    private boolean judgeExecute(Class<?> clazz) throws ClassNotFoundException {
        for (Class<?> aClass : clazz.getInterfaces()) {
            if(aClass.equals(Class.forName(TASK))){
                System.out.println(true);
                return true;
            }
        }
        return false;
    }
}
