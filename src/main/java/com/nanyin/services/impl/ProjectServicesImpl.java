package com.nanyin.services.impl;

import com.nanyin.entity.ProjectLevel;
import com.nanyin.entity.ProjectStatus;
import com.nanyin.entity.ProjectType;
import com.nanyin.repository.ProjectLevelRepository;
import com.nanyin.repository.ProjectStatusRepository;
import com.nanyin.repository.ProjectTypeRepository;
import com.nanyin.services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectServicesImpl implements ProjectServices {

    @Autowired
    ProjectLevelRepository projectLevelRepository;
    @Autowired
    ProjectTypeRepository projectTypeRepository;
    @Autowired
    ProjectStatusRepository projectStatusRepository;

    @Override
    @Cacheable("getStandardProjectStatus")
    public  Set<ProjectStatus> getStandardProjectStatus() throws Exception {
        return projectStatusRepository.findByOrderByOrdAsc();
    }

    @Override
    @Cacheable("getStandardProjectLevel")
    public Set<ProjectLevel> getStandardProjectLevel()throws Exception {
        return projectLevelRepository.findByOrderByOrdAsc();
    }

    @Override
    @Cacheable("getStandardProjectType")
    public Set<ProjectType> getStandardProjectType() throws Exception {
        return projectTypeRepository.findByOrderByOrdAsc();
    }
}
