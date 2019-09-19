package com.nanyin.config.quartz.repository;

import com.nanyin.config.quartz.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by NanYin on 2019/9/18.
 */
public interface JobEntityRepository extends JpaRepository<JobEntity,Integer> {
    JobEntity getById(Integer id);
}
