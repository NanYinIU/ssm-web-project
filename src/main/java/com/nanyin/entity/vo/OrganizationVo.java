package com.nanyin.entity.vo;

import com.nanyin.entity.Organization;

/**
 * Created by NanYin on 2017-07-18 下午2:22.
 * 包名： com.nanyin.entity.ex
 * 类描述： 部门的拓展类 包含组织的所有信息
 */
public class OrganizationVo extends Organization {
    private String motto;
    private int pId ;

    public String getMottol() {
        return motto;
    }

    public void setMottol(String mottol) {
        this.motto = mottol;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "OrganizationEx{" +
                "mottol='" + motto + '\'' +
                ", pId=" + pId +
                '}';
    }
}
