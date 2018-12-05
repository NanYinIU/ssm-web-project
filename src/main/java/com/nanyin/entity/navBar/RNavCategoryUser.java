package com.nanyin.entity.navBar;

/**
 * @Auther: NanYin
 * @Date: 12/5/18 21:52
 * @Description:
 */
public class RNavCategoryUser {
    public Integer id;
    public Integer  navCategoryId;
    public Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNavCategoryId() {
        return navCategoryId;
    }

    public void setNavCategoryId(Integer navCategoryId) {
        this.navCategoryId = navCategoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}