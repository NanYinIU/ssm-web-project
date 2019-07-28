package com.nanyin.services;

import com.nanyin.entity.ProjectLevel;
import com.nanyin.entity.ProjectStatus;
import com.nanyin.entity.ProjectType;

import java.util.List;
import java.util.Set;

public interface ProjectServices {
     Set<ProjectStatus> getStandardProjectStatus();

     Set<ProjectLevel> getStandardProjectLevel();

     Set<ProjectType> getStandardProjectType();


}
