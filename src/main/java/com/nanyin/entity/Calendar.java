package com.nanyin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by NanYin on 2017-08-15 下午2:10.
 * 包名： com.nanyin.entity
 * 类描述：
 */
@Component
public class Calendar {
    private int id;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp plan_start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp plan_end;
    private int allDay;
    private String color;
    private int className;
    private int u_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getPlan_start() {
        return plan_start;
    }

    public void setPlan_start(Timestamp plan_start) {
        this.plan_start = plan_start;
    }

    public Timestamp getPlan_end() {
        return plan_end;
    }

    public void setPlan_end(Timestamp plan_end) {
        this.plan_end = plan_end;
    }

    public int getAllDay() {
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getClassName() {
        return className;
    }

    public void setClassName(int className) {
        this.className = className;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

}
