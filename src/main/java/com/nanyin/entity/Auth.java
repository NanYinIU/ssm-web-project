package com.nanyin.entity;

import java.util.Date;

/**
 * Created by NanYin on 2017-07-10 下午2:19.
 * 包名： com.nanyin.entity.vo
 * 类描述：权限表的实体类
 */
public class Auth {
    private int id ;
    private String name;
    private int ord;
    private String comm;
    private Date create_data;

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

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public Date getCreate_data() {
        return create_data;
    }

    public void setCreate_data(Date create_data) {
        this.create_data = create_data;
    }
}
