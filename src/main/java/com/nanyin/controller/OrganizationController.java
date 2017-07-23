package com.nanyin.controller;

import com.nanyin.entity.Organization;
import com.nanyin.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by NanYin on 2017-07-12 下午4:53.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/org")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;
    @RequestMapping("selectOrganizationById")

    public @ResponseBody String selectOrganizationById(int id){
       return organizationService.selectOrganizationById(id);
    }

    @RequestMapping(value = "/updateOrgName"
            ,consumes = "application/json" , method = RequestMethod.POST)
    public @ResponseBody int updateOrgName(@RequestBody Organization organization) throws Exception{
        int id = organization.getId();
        String name = organization.getName();
        System.out.println(id+" ," +name);
        return organizationService.updateById(organization);
    }

    @RequestMapping("/delectOrgNode")
    public @ResponseBody int delectOrgNode(@RequestBody Organization organization) throws Exception{
        int id = organization.getId();
        return organizationService.delectOrgById(id);
    }
    @RequestMapping("/returnOrgPage")
    public String returnOrgPage(){
        return "/static/fondPage/organization/DisOrganization.jsp";
    }
}
