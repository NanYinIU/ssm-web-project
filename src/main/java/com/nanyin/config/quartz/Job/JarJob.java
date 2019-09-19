package com.nanyin.config.quartz.Job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NanYin on 2019/9/18.
 */
public class JarJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(JarJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String jarPath = map.getString("jarPath");
        String parameter = map.getString("parameter");
        String vmParam = map.getString("vmParam");
        String fullClassName = map.getString("jobClass");
//                 logger.info("Running Job name : {} ", map.getString("name"));
//                 logger.info("Running Job description : " + map.getString("JobDescription"));
//                 logger.info("Running Job group: {} ", map.getString("group"));
//                 logger.info("Running Job cron : " + map.getString("cronExpression"));
//                 logger.info("Running Job jar path : {} ", jarPath);
//                 logger.info("Running Job parameter : {} ", parameter);
//                 logger.info("Running Job vmParam : {} ", vmParam);
        long startTime = System.currentTimeMillis();
        if (jarPath != null && !"".equals(jarPath)) {
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
                processBuilder.command(commands);
                logger.info("Running Job details as follows >>>>>>>>>>>>>>>>>>>>: ");
                logger.info("Running Job commands : {}  ", commands);
                try {
                    Process process = processBuilder.start();
                } catch (IOException e) {
                    throw new JobExecutionException(e);
                }
            }else if(fullClassName!=null && !"".equals(fullClassName)){
                // 通过全类名 进行反射的调用

            }else{
                // 否则 logger 打印 日志
            }
        }
    }
}
