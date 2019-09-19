package com.nanyin.services;

import com.nanyin.entity.Project;
import com.nanyin.entity.ProjectLevel;
import com.nanyin.entity.ProjectStatus;
import com.nanyin.entity.ProjectType;

import java.util.Set;

public interface ProjectService {
     Set<ProjectStatus> getStandardProjectStatus() throws Exception;

     Set<ProjectLevel> getStandardProjectLevel() throws Exception;

     Set<ProjectType> getStandardProjectType() throws Exception;

    Set<Project> getProjects(String projectStatus, String projectLevel, String projectType);
}
