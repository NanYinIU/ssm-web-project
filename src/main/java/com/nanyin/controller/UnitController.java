package com.nanyin.controller;

import com.nanyin.entity.Organization;
import com.nanyin.entity.vo.OrganizationVo;
import com.nanyin.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by NanYin on 2017-07-12 下午4:53.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/org")
public class UnitController {

    @Autowired
    UnitService unitService;
    @RequestMapping("selectOrganizationById")

    public @ResponseBody String selectOrganizationById(int id){
       return unitService.selectOrganizationById(id);
    }

    @RequestMapping(value = "/updateOrgName"
            ,consumes = "application/json" , method = RequestMethod.POST)
    public @ResponseBody int updateOrgName(@RequestBody Organization organization) throws Exception{
        int id = organization.getId();
        String name = organization.getName();
        System.out.println(id+" ," +name);
        return unitService.updateById(organization);
    }

    @RequestMapping("/delectOrgNode")
    public @ResponseBody int delectOrgNode(@RequestBody Organization organization) throws Exception{
        int id = organization.getId();
        return unitService.delectOrgById(id);
    }
    @RequestMapping("/returnOrgPage")
    public String returnOrgPage(){
        return "/static/fondPage/organization/DisOrganization.jsp";
    }

    @RequestMapping(value = "/insertOrg"
            ,method = RequestMethod.POST)
    public @ResponseBody int insertOrg(String name , Integer pId){
        OrganizationVo organization = new OrganizationVo();
        organization.setName(name);
        organization.setpId(pId);
        return unitService.insertOrg(organization);
    }

}
