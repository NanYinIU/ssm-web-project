package com.nanyin.common.format;

import java.util.List;

/**
 * Created by NanYin on 2017-08-17 下午2:46.
 * 包名： com.nanyin.common.format
 * 类描述：
 */
public class PermissionFormat {
    int roleId;
    private List<Integer> permissionId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(List<Integer> permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "PermissionFormat{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}
