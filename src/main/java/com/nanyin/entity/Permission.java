package com.nanyin.entity;

/**
 * Created by NanYin on 2017-07-10 下午2:19.
 * 包名： com.nanyin.entity.vo
 * 类描述：权限表的实体类
 */
public class Permission {
    private int id ;
    private String permission_name;
    private String decript;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public String getDecript() {
        return decript;
    }

    public void setDecript(String decript) {
        this.decript = decript;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permission_name='" + permission_name + '\'' +
                ", decript='" + decript + '\'' +
                '}';
    }
}
