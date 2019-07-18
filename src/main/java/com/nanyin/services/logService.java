package com.nanyin.services;

import com.nanyin.entity.Log;

import java.util.Map;

/**
 * Created by NanYin on 2017-07-16 下午11:03.
 * 包名： com.nanyin.services
 * 类描述：
 */
public interface logService {
    int deleteSystemLog(String id);

    int insert(Log record);

    int insertTest(Log record);

    Map<String,Object> selectSystemLog(Map<String,Object> map);

    int updateSystemLog(Log record);
}
