package com.nanyin.mapper;

import com.nanyin.entity.Auth;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by NanYin on 2017-08-16 下午10:41.
 * 包名： com.nanyin.mapper
 * 类描述：
 */
@Repository
public interface AuthMapper {

    Set<String> getPermission(String name);

}
