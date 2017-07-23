package com.nanyin.entity;

/**
 * Created by NanYin on 2017-07-10 下午1:27.
 * 包名： com.nanyin.entity
 * 类描述：部门的实体类
 */
public class Organization {
    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
