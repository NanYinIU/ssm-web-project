package com.nanyin.mapper;

import com.nanyin.entity.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-16 下午11:00.
 * 包名： com.nanyin.mapper
 * 类描述：log dao层
 */
public interface LogMapper {

    int deleteByPrimaryKey(String id);

    int insert(Log record);

    int insertSelective(Log record);

    List<Log> select(Map<String,Object> map);


    List<Log> selectAll();



    int updateByPrimaryKeySelective(Log record);


}
