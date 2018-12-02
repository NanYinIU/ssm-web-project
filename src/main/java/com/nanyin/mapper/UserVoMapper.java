package com.nanyin.mapper;

import com.nanyin.entity.user.User;
import com.nanyin.entity.user.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-10 下午2:22.
 * 包名： com.nanyin.mapper
 * 类描述：
 */
public interface UserVoMapper {
//    全部人员信息输出
    List<UserVo> AllMessageDis();
//    根据名字进行模糊查询
    List<UserVo> selectMsgByName(String name);

    List<UserVo> selectByName(Map<String,Object> map);
//    批量删除
    int delectParam(List<UserVo> list);

    int insertUserVo(User user);

    int UpdateUserVo(User user);
}
