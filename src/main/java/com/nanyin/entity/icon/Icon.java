package com.nanyin.entity.icon;

/**
 * @Auther: NanYin
 * @Date: 12/8/18 17:18
 * @Description:
 */
public class Icon {

    private Integer id;
    private String iconName;
    private String iconUnicode;
    private String iconClass;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconUnicode() {
        return iconUnicode;
    }

    public void setIconUnicode(String iconUnicode) {
        this.iconUnicode = iconUnicode;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }
}