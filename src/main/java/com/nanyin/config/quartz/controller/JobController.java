package com.nanyin.config.quartz.controller;

import com.nanyin.config.quartz.Job.DefaultJob;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by NanYin on 2019/9/18.
 * Job rest     get /job/{id} 启动id为{id} 的任务
 */
@Controller
public class JobController {
    private Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    JobService jobService;
    @Autowired
    JobEntityRepository jobEntityRepository;

    //根据ID重启某个Job
    @GetMapping("/job/{id}")
    public @ResponseBody String StartOrStopJob(@PathVariable Integer id,String type) throws SchedulerException {
        String result = "";
        try {
            if(type.equals("open")){
                result = jobService.openAssignJob(id);
            }else if(type.equals("close")){
                result = jobService.closeAssignJob(id);
            }else result = "wrong status";
        } catch (Exception e) {

        }
        return result;
    }

    @GetMapping("/jobs")
    public @ResponseBody String StartOrStopJobs(String type) throws SchedulerException {
        String result = "";
        try {
            if(type.equals("open")){
                jobService.startAllJobs();
            }else if(type.equals("close")){
                jobService.stopAllJobs();
            }else result = "wrong status";
        } catch (Exception e) {

        }
        return result;
    }

    // 修改和删除定时任务内容，并且默认将修改后的状态置为close，并且关闭定时任务。



    @GetMapping("/stopJob/{id}")
    public @ResponseBody String stop(@PathVariable Integer id ){
        String result = "";
        try {
            result = jobService.closeAssignJob(id);
        } catch (Exception e) {

        }
        return result;
    }

}
