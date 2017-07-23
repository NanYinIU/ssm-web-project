package com.nanyin.mapper;

import com.nanyin.entity.SystemLog;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-16 下午11:00.
 * 包名： com.nanyin.mapper
 * 类描述：log dao层
 */
public interface SystemLogMapper {

    int deleteByPrimaryKey(String id);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    List<SystemLog> select(Map<String,Object> map);


    List<SystemLog> selectAll();



    int updateByPrimaryKeySelective(SystemLog record);


}
