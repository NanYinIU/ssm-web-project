package com.nanyin.controller;

import com.nanyin.common.format.PermissionFormat;
import com.nanyin.common.select2;
import com.nanyin.entity.Permission;
import com.nanyin.services.PermissionService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-08-16 下午11:00.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/per")
public class PermissionController {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    PermissionService permissionService;

    private List<Integer> permissionId;

    @RequestMapping(value = "/AllPermission")
    public @ResponseBody List<select2> AllPermission(){
        return permissionService.selectAllPermission();
    }

    @RequestMapping(value = "/InsertPermission"
            ,consumes = "application/json" , method = RequestMethod.POST)
    public @ResponseBody int InsertPermission(@RequestBody PermissionFormat permissionFormat){
            logger.info(permissionFormat);
            int roleid = permissionFormat.getRoleId();
            logger.info(roleid);
            List<Integer> permissionId = permissionFormat.getPermissionId();
            logger.info(permissionId);
            Map<String ,Object> map = new HashMap<String, Object>();
            map.put("r_id",roleid);
            map.put("p_id",permissionId);

            return permissionService.insertPermissionById(map);
    }

}
