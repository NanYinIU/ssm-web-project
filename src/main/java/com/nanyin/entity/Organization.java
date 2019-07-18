package com.nanyin.entity;

import java.util.Date;

/**
 * Created by NanYin on 2017-07-10 下午1:27.
 * 包名： com.nanyin.entity
 * 类描述：部门的实体类
 */
public class Organization {
    private int id;
    private String name;
    private int parent_id;
    private String code;
    private String address;
    private String phone;
    private Date create_date;
    private String description;
    private Integer is_deleted;
    private String delete_comm;

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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getDelete_comm() {
        return delete_comm;
    }

    public void setDelete_comm(String delete_comm) {
        this.delete_comm = delete_comm;
    }
}
