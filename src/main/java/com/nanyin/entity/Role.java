package com.nanyin.entity;

import java.util.Date;

/**
 * Created by NanYin on 2017-07-10 下午1:27.
 * 包名： com.nanyin.entity
 * 类描述：角色的实体类
 */
public class Role {
    private int id;
    private String name;
    private String ord;
    private String comm;
    private Date create_date;

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

    public String getOrd() {
        return ord;
    }

    public void setOrd(String ord) {
        this.ord = ord;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
