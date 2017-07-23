package com.nanyin.services;

import com.nanyin.entity.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-10 下午2:33.
 * 包名： com.nanyin.services
 * 类描述：
 */

public interface UserVoService{

    List<UserVo> AllMessageDis();

    List<UserVo> selectMsgByName(String name);

    Map<String,Object> selectByName(Map<String,Object> map);

    int delectParam(List<UserVo> list);

    int insertUserVo(UserVo userVo);

    int UpdateUserVo(UserVo userVo);
}
