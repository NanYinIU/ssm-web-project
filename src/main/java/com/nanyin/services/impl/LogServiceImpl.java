package com.nanyin.services.impl;

import com.nanyin.entity.Log;
import com.nanyin.mapper.LogMapper;
import com.nanyin.services.logService;
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
public class LogServiceImpl implements logService {


    @Autowired
    private LogMapper logMapper;

    public int deleteSystemLog(String id) {
        return logMapper.deleteByPrimaryKey(id);
    }

    public int insert(Log record) {
        return logMapper.insertSelective(record);
    }

    public int insertTest(Log record) {
        return logMapper.insert(record);
    }

    public Map<String,Object> selectSystemLog(Map<String,Object> map) {

        Map<String,Object> result = new HashMap<String, Object>();
        List<Log> row = logMapper.selectAll();
        List<Log> rows = logMapper.select(map);
        int total = row.size();
        result.put("rows",rows);
        result.put("total",total);
        return result;

    }

    public int updateSystemLog(Log record) {
        return logMapper.updateByPrimaryKeySelective(record);
    }
}
