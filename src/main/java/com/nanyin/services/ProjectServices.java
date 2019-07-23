package com.nanyin.services;

import com.nanyin.entity.ProjectLevel;
import com.nanyin.entity.ProjectStatus;
import com.nanyin.entity.ProjectType;
import com.nanyin.entity.wrapper.StandardWrapper;

import java.util.List;

public interface ProjectServices {
     List<ProjectStatus> getStandardProjectStatus();

     List<ProjectLevel> getStandardProjectLevel();

     List<ProjectType> getStandardProjectType();


}
