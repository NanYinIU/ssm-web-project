package com.nanyin.config.quartz.controller;

import com.nanyin.config.quartz.Job.JarJob;
import com.nanyin.config.quartz.Job.SimpleJob;
import com.nanyin.config.quartz.JobEntity;
import com.nanyin.config.quartz.JobStatus;
import com.nanyin.config.quartz.repository.JobEntityRepository;
import com.nanyin.config.quartz.service.JobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by NanYin on 2019/9/18.
 */
@Controller
public class JobController {
    private Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    JobService jobService;
    @Autowired
    JobEntityRepository timingTaskEntityRepository;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    //根据ID重启某个Job
    @GetMapping("/job/{id}")
    public String refresh(@PathVariable Integer id) throws SchedulerException {
        String result;
        JobEntity entity = timingTaskEntityRepository.getById(id);
        if (entity == null) return "error: id is not exist ";
        synchronized (logger) {
            JobKey jobKey = jobService.getJobKey(entity);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.pauseJob(jobKey);
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
            JobDataMap map = jobService.getJobDataMap(entity);
            JobDetail jobDetail = jobService.getJobDetail(jobKey, entity.getDescription(), map, JarJob.class);
            if (entity.getStatus().equals("OPEN")) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
                result = "Refresh Job : " + entity.getName() + "\t jarPath: " + entity.getJarPath() + " success !";
            } else {
                result = "Refresh Job : " + entity.getName() + "\t jarPath: " + entity.getJarPath() + " failed ! , " +
                        "Because the Job status is " + entity.getStatus();
            }
        }
        return result;
    }

    @GetMapping("/jobClass/{id}")
    public @ResponseBody String refreshWithClass(@PathVariable Integer id) throws SchedulerException {
        String result;
        JobEntity entity = timingTaskEntityRepository.getById(id);
        if (entity == null) return "error: id is not exist ";
        synchronized (logger) {
            JobKey jobKey = jobService.getJobKey(entity);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.pauseJob(jobKey);
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
            JobDataMap map = jobService.getJobDataMap(entity);
            JobDetail jobDetail = jobService.getJobDetail(jobKey, entity.getDescription(), map, SimpleJob.class);
            if (entity.getStatus().equals(JobStatus.OPEN)) {
                scheduler.scheduleJob(jobDetail, jobService.getTrigger(entity));
                result = "Refresh Job : " + entity.getName() + "\t jarPath: " + entity.getJarPath() + " success !";
            } else {
                result = "Refresh Job : " + entity.getName() + "\t jarPath: " + entity.getJarPath() + " failed ! , " +
                        "Because the Job status is " + entity.getStatus();
            }
        }
        return result;
    }
}
