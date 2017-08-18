package com.nanyin.controller;

import com.nanyin.entity.Organization;
import com.nanyin.entity.vo.OrganizationVo;
import com.nanyin.services.OrganizationVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-18 下午2:35.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/organization")
public class OrganizationVoController {
    @Autowired
    OrganizationVoService organizationVoService;
    @RequestMapping(value = "selectOrganizationVo"
            ,consumes = "application/json" , method = RequestMethod.POST)
    public @ResponseBody List<OrganizationVo> list(){
        System.out.println(organizationVoService.selectOrganizationVo());
        return organizationVoService.selectOrganizationVo();
    }

    @RequestMapping("/displayOrganizationVo")
    public @ResponseBody Map<String, Object> displayOrganizationVo(int pageNumber, int pageSize){
        int a=(pageNumber-1)*pageSize;
        int b=pageSize;
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("a",a);
        param.put("b",b);
        return organizationVoService.displayOrganizationVo(param);
    }

    @RequestMapping("/updateNode")
    public @ResponseBody int updateNode(@RequestBody OrganizationVo organizationVo){
        return organizationVoService.updateNode(organizationVo);
    }

    @RequestMapping("/resEchart")
    public @ResponseBody Map resEchart(){
        Map<String,List> map = new HashMap<String,List>();
        map.put("list",organizationVoService.orgEchart());
        return map;
    }
}
