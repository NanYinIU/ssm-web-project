package com.nanyin.config.quartz.service;

import com.nanyin.config.quartz.Job.SimpleJob;
import com.nanyin.config.quartz.JobEntity;
import com.nanyin.config.quartz.repository.JobEntityRepository;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by NanYin on 2019/9/18.
 */
@Service
public class JobService {

    Logger logger = LoggerFactory.getLogger(JobService.class);
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private JobEntityRepository jobEntityRepository;

    private List<JobEntity> loadJobs(){
        return jobEntityRepository.findAll();
    }
    /**
     * 获取定时任务的定义
     * JobDetail是任务的定义,Job是任务的执行逻辑
     *
     * @param jobKey      定时任务的名称 组名
     * @param description 定时任务的 描述
     * @param jobDataMap  定时任务的 元数据
     * @param jobClass    {@link org.quartz.Job} 定时任务的 真正执行逻辑定义类
     */
    public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap jobDataMap, Class<? extends Job>
            jobClass) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(jobDataMap)
                .usingJobData(jobDataMap)
                .requestRecovery()
                .storeDurably()
                .build();
    }

    /**
     * 获取Trigger (Job的触发器,执行规则)
     *
     * @param jobKey         定时任务的名称 组名
     * @param description    定时任务的 描述
     * @param jobDataMap     定时任务的 元数据
     * @param cronExpression 定时任务的 执行cron表达式
     */
    public Trigger getTrigger(JobKey jobKey, String description, JobDataMap jobDataMap, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .withDescription(description)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .usingJobData(jobDataMap)
                .build();
    }

    public Trigger getTrigger(JobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getName(), job.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }

    public JobDataMap getJobDataMap(JobEntity job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getName());
        map.put("group", job.getGroup());
        map.put("cronExpression", job.getCron());
        map.put("parameter", job.getParameter());
        map.put("JobDescription", job.getDescription());
        map.put("vmParam", job.getVmParam());
        map.put("jarPath", job.getJarPath());
        map.put("status", job.getStatus());
        return map;
    }

    //获取JobKey,包含Name和Group
    public JobKey getJobKey(JobEntity timingTaskEntity) {
        return JobKey.jobKey(timingTaskEntity.getName(), timingTaskEntity.getGroup());
    }

    /**
     * 开启所有状态为open的定时任务
     * @throws Exception
     */
    public void startAllJobs() throws Exception{
        synchronized (this.getClass()) {                                                         //只允许一个线程进入操作
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
            scheduler.pauseJobs(GroupMatcher.anyGroup());                               //暂停所有JOB
            for (JobKey jobKey : set) {                                                 //删除从数据库中注册的所有JOB
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                scheduler.deleteJob(jobKey);
            }
            for (JobEntity job : loadJobs()) {                               //从数据库中注册的所有JOB
                logger.info("Job register name : {} , group : {} , cron : {}", job.getName(), job.getGroup(), job.getCron());
                JobDataMap map = getJobDataMap(job);
                JobKey jobKey = getJobKey(job);
                JobDetail jobDetail = getJobDetail(jobKey, job.getDescription(), map, SimpleJob.class);
                if (job.getStatus().equals("OPEN")) scheduler.scheduleJob(jobDetail, getTrigger(job));
                else
                    logger.info("Job jump name : {} , Because {} status is {}", job.getName(), job.getName(), job.getStatus());
            }
        }
    }
}
