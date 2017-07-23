package com.nanyin.services.impl;

import com.nanyin.entity.SystemLog;
import com.nanyin.entity.vo.UserVo;
import com.nanyin.mapper.SystemLogMapper;
import com.nanyin.services.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by NanYin on 2017-07-16 下午11:04.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
@Service
public class SystemLogServiceImpl implements SystemLogService{


    @Autowired
    private SystemLogMapper systemLogMapper;

    public int deleteSystemLog(String id) {
        return systemLogMapper.deleteByPrimaryKey(id);
    }

    public int insert(SystemLog record) {
        return systemLogMapper.insertSelective(record);
    }

    public int insertTest(SystemLog record) {
        return systemLogMapper.insert(record);
    }

    public Map<String,Object> selectSystemLog(Map<String,Object> map) {

        Map<String,Object> result = new HashMap<String, Object>();
        List<SystemLog> row = systemLogMapper.selectAll();
        List<SystemLog> rows = systemLogMapper.select(map);
        int total = row.size();
        result.put("rows",rows);
        result.put("total",total);
        return result;

    }

    public int updateSystemLog(SystemLog record) {
        return systemLogMapper.updateByPrimaryKeySelective(record);
    }
}
