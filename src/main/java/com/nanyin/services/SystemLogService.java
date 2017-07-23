package com.nanyin.services;

import com.nanyin.entity.SystemLog;

import java.util.Map;

/**
 * Created by NanYin on 2017-07-16 下午11:03.
 * 包名： com.nanyin.services
 * 类描述：
 */
public interface SystemLogService {
    int deleteSystemLog(String id);

    int insert(SystemLog record);

    int insertTest(SystemLog record);

    Map<String,Object> selectSystemLog(Map<String,Object> map);

    int updateSystemLog(SystemLog record);
}
