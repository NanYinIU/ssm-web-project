package com.nanyin.common;

/**
 * Created by NanYin on 2017-08-16 下午11:10.
 * 包名： com.nanyin.common
 * 类描述：
 */
public class select2 {
    int id;
    String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "select2{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
