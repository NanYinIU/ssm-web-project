package com.nanyin.mapper;

import com.nanyin.entity.wrapper.Standard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
@Mapper
public interface ProjectMapper {

    @Select("select id,name from s_project_status")
    public List<Standard> getStandardProjectStatus();

    @Select("select id,name from s_project_level")
    public List<Standard> getStandardProjectLevel();

    @Select("select id,name from s_project_type")
    public List<Standard> getStandardProjectType();
}
