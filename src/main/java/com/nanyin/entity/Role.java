package com.nanyin.entity;

/**
 * Created by NanYin on 2017-07-10 下午1:27.
 * 包名： com.nanyin.entity
 * 类描述：角色的实体类
 */
public class Role {
    private int role_id;
    private String role_name;
    private String describe;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
