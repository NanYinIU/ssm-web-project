package com.nanyin.services.impl;

import com.nanyin.entity.wrapper.StandardWrapper;
import com.nanyin.services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServicesImpl implements ProjectServices {
//    @Autowired
//    ProjectMapper projectMapper;

    @Override
    public List<StandardWrapper> getStandardProjectStatus() {
        return null;
    }

    @Override
    public List<StandardWrapper> getStandardProjectLevel() {
        return null;
    }

    @Override
    public List<StandardWrapper> getStandardProjectType() {
        return null;
    }
}
