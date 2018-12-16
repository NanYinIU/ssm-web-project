package com.nanyin.entity.navBar.vo;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:31
 * @Description: 导航栏内容实体类
 */
public class NavBarInfos {
    public Integer id;
    public String name;
    public String href;
    public Integer parentNavId;
    public String icon;
    public Integer isDeleted;
    public Integer ord;
    public Integer spread;

    public Integer getSpread() {
        return spread;
    }

    public void setSpread(Integer spread) {
        this.spread = spread;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

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

    public Integer getParentNavId() {
        return parentNavId;
    }

    public void setParentNavId(Integer parentNavId) {
        this.parentNavId = parentNavId;
    }

}