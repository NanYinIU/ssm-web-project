package com.nanyin.services.impl;

import com.nanyin.entity.wrapper.Standard;
import com.nanyin.mapper.ProjectMapper;
import com.nanyin.services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ProjectServicesImpl implements ProjectServices {
    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<Standard> getStandardProjectStatus() {
        return projectMapper.getStandardProjectStatus();
    }

    @Override
    public List<Standard> getStandardProjectLevel() {
        return projectMapper.getStandardProjectLevel();
    }

    @Override
    public List<Standard> getStandardProjectType() {
        return projectMapper.getStandardProjectType();
    }
}
