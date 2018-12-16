package com.nanyin.entity.navBar.vo;

import com.nanyin.entity.navBar.NavBar;

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
    List<NavBarInfos> children;

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

    public List<NavBarInfos> getChildren() {
        return children;
    }

    public void setChildren(List<NavBarInfos> children) {
        this.children = children;
    }
}