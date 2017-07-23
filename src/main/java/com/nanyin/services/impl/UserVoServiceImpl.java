package com.nanyin.services.impl;

import com.nanyin.entity.User;
import com.nanyin.entity.vo.UserVo;
import com.nanyin.mapper.OrganizationMapper;
import com.nanyin.mapper.RoleMapper;
import com.nanyin.mapper.UserVoMapper;
import com.nanyin.services.UserVoService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by NanYin on 2017-07-10 下午2:34.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
@Service
public class UserVoServiceImpl implements UserVoService {
    @Autowired
    private UserVoMapper userVoMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private OrganizationMapper organizationMapper;


    public List<UserVo> AllMessageDis() {
        return userVoMapper.AllMessageDis();
    }

    public List<UserVo> selectMsgByName(String name) {


        return userVoMapper.selectMsgByName(name);
    }

    public Map<String, Object> selectByName(Map<String, Object> map) {
       List<UserVo> row =  userVoMapper.AllMessageDis();
        Map<String,Object> result = new HashMap<String, Object>();

       List<UserVo> rows = userVoMapper.selectByName(map);
       int total = row.size();
        result.put("rows",rows);
        result.put("total",total);
       return result;
    }
    public int delectParam(List<UserVo> list){
        return userVoMapper.delectParam(list);
    }

    public int insertUserVo(UserVo userVo) throws NullPointerException {

//        将未传进来的属性附值 salt默认还是salt date时间 根据rolename 和 organ_name 查询role_id
//          和 organlization_id 然后都set都user的list表中
        User user = new User();

        user.setName(userVo.getName());
        user.setSalt("salt");
        user.setCreate_time(new Date());
        user.setPassword(new SimpleHash("MD5",userVo.getPassword(),"salt",1024).toString());
        user.setAge(userVo.getAge());
        user.setSex(userVo.getSex());

        String describe = userVo.getRole_describe();

        if(describe!=null&&!"".equals(describe)) {
            int role_id = roleMapper.selectByRoleName(describe);
            user.setRoleId(role_id);
        }
//        如果没有传进name值默认分配roleid=5 也就是guest角色
        else {
            user.setRoleId(5);
        }
        String OrganName = userVo.getOrganization_name();
        if(!"".equals(OrganName)&&OrganName!=null) {
           int organ_id = organizationMapper.selectOrganizationByName(OrganName);
            user.setOrganizationId(organ_id);
        }else{
            user.setOrganizationId(5);
        }
        userVoMapper.insertUserVo(user);
        return 0;
    }

    public int UpdateUserVo(UserVo userVo) {
        User user = new User();
        user.setId(userVo.getId());
        user.setName(userVo.getName());
        user.setPassword(new SimpleHash("MD5",userVo.getPassword(),"salt",1024).toString());
        user.setAge(userVo.getAge());
        user.setSex(userVo.getSex());

        String describe = userVo.getRole_describe();

        if(describe!=null&&!"".equals(describe)) {
            int role_id = roleMapper.selectByRoleName(describe);
            user.setRoleId(role_id);
        }
//        如果没有传进name值默认分配roleid=5 也就是guest角色
        else {
            user.setRoleId(5);
        }
        String OrganName = userVo.getOrganization_name();
        if(!"".equals(OrganName)&&OrganName!=null) {
            int organ_id = organizationMapper.selectOrganizationByName(OrganName);
            user.setOrganizationId(organ_id);
        }else{
            user.setOrganizationId(5);
        }

        return userVoMapper.UpdateUserVo(user);
    }



}
