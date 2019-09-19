package com.nanyin.controller;

import com.nanyin.services.ProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @RequiresPermissions({"admin"})
    @GetMapping("/projects")
    public String projects(Model model,String projectStatus,String projectLevel,String projectType){
        try {
            // 是否先放到session里，然后判断session中是否存在
            model.addAttribute("projectStatus", projectService.getStandardProjectStatus());
            model.addAttribute("projectLevel", projectService.getStandardProjectLevel());
            model.addAttribute("projectType", projectService.getStandardProjectType());
            // project 基本信息
            model.addAttribute("projects", projectService.getProjects(projectStatus,projectLevel,projectType));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "projects";
    }

}
