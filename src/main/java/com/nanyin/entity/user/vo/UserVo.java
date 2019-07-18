package com.nanyin.entity.user.vo;

import com.nanyin.entity.user.User;



/**
 * Created by NanYin on 2017-07-08 下午5:43.
 * 包名： com.nanyin.entity.vo
 * 类描述： user 的包装类 传输对象
 */
public class UserVo extends User{

    private String role_describe;
    private String organization_name;



    public String getRole_describe() {
        return role_describe;
    }

    public void setRole_describe(String role_describe) {
        this.role_describe = role_describe;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "role_describe='" + role_describe + '\'' +
                ", organization_name='" + organization_name + '\'' +
                '}';
    }
}
