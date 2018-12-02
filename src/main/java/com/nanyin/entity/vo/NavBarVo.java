package com.nanyin.entity.vo;

import com.nanyin.entity.NavBar;

import java.util.List;

/**
 * @Auther: NanYin
 * @Date: 11/28/18 20:54
 * @Description:
 */
public class NavBarVo {
    Integer id;
    String name;
    String href;
    String icon;
    Integer spread;
    List<NavBar> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSpread() {
        return spread;
    }

    public void setSpread(Integer spread) {
        this.spread = spread;
    }

    public List<NavBar> getChildren() {
        return children;
    }

    public void setChildren(List<NavBar> children) {
        this.children = children;
    }
}