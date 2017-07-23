package com.nanyin.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import java.util.Date;

/**
 * Created by NanYin on 17-7-8.
 * 用户的基本注册信息 不包括部门 权限 职位等信息
 */
public class User {
    private int id;
    @NotBlank(message="不能为空")
    @Length(max=12, min=3, message="用户名在3到30位之间")
    private String name;
    @NotBlank(message="不能为空")
    @Length(max=12, min=3, message="密码在3到30位之间")
    private String password;
    private String salt;
    private int roleId;
    @Max(value=100, message="{年龄不能超过100}")
    private int age;
    private String sex;
    private Date create_time;
    private int organizationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", roleId=" + roleId +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", create_time=" + create_time +
                ", organizationId=" + organizationId +
                '}';
    }
}
