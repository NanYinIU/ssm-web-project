package com.nanyin.services;

import com.nanyin.common.select2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-08-16 下午10:42.
 * 包名： com.nanyin.services
 * 类描述：
 */
@Service
public interface AuthService {

    List<select2> selectAllPermission();

    int insertPermissionById(Map<String,Object> map);
}
