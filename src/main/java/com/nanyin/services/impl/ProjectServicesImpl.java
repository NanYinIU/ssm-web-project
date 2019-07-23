package com.nanyin.services.impl;

import com.nanyin.entity.ProjectLevel;
import com.nanyin.entity.ProjectStatus;
import com.nanyin.entity.ProjectType;
import com.nanyin.repository.ProjectLevelRepository;
import com.nanyin.repository.ProjectStatusRepository;
import com.nanyin.repository.ProjectTypeRepository;
import com.nanyin.services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServicesImpl implements ProjectServices {

    @Autowired
    ProjectLevelRepository projectLevelRepository;
    @Autowired
    ProjectTypeRepository projectTypeRepository;
    @Autowired
    ProjectStatusRepository projectStatusRepository;

    @Override
    public  List<ProjectStatus> getStandardProjectStatus() {
        return projectStatusRepository.findByOrderByOrdAsc();
    }

    @Override
    public List<ProjectLevel> getStandardProjectLevel() {
        return projectLevelRepository.findByOrderByOrdAsc();
    }

    @Override
    public List<ProjectType> getStandardProjectType() {
        return projectTypeRepository.findByOrderByOrdAsc();
    }
}
