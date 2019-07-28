package com.nanyin.controller;

import com.nanyin.services.ProjectServices;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProjectController {

    @Autowired
    ProjectServices projectServices;

    @RequiresPermissions({"admin"})
    @GetMapping("/projects")
    public String projects(Model model){
        try {
            model.addAttribute("projectStatus",projectServices.getStandardProjectStatus());
            model.addAttribute("projectLevel",projectServices.getStandardProjectLevel());
            model.addAttribute("projectType",projectServices.getStandardProjectType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "projects";
    }

}
