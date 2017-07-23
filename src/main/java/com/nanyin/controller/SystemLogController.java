package com.nanyin.controller;

import com.nanyin.common.annotation.Log;
import com.nanyin.entity.SystemLog;
import com.nanyin.services.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-17 下午2:35.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/log")
public class SystemLogController {

    @Autowired
    SystemLogService systemLogService;
    @RequestMapping("/selectSysLog")
//    @Log(operationType = "select操作" ,operationName = "日志查询")
    public @ResponseBody
    Map<String ,Object> selectSysLog(int pageSize, int pageNumber){
        int a=(pageNumber-1)*pageSize;
        int b=pageSize;
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("a", a);
        param.put("b", b);
        return systemLogService.selectSystemLog(param);
    }
    @RequestMapping("/returnLog")
    public String returnLog(){
        return "/static/fondPage/log/operateLog.jsp";
    }
}
