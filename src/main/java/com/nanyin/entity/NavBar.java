package com.nanyin.entity;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:31
 * @Description: 导航栏内容实体类
 */
public class NavBar {
    Integer id;
    String name;
    String url;
    String clazz;
    Integer parentNavId;
    String icon;
    Integer isDeleted;
    Integer ord;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Integer getParentNavId() {
        return parentNavId;
    }

    public void setParentNavId(Integer parentNavId) {
        this.parentNavId = parentNavId;
    }

    @Override
    public String toString() {
        return "NavBar{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", clazz='" + clazz + '\'' +
                ", parentNavId=" + parentNavId +
                '}';
    }
}