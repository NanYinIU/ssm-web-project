package com.nanyin.mapper;

import com.nanyin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("select * from user where name=#{name} ")
    User getUserFromUserName(String name);

}
