package com.nanyin.entity;

import java.util.Date;

/**
 * Created by NanYin on 2017-07-16 下午10:56.
 * 包名： com.nanyin.entity
 * 类描述：日志实体类
 */
public class SystemLog {
    private String id;
    private String description;
    private String method;
    private String logType;
    private String requestIp;
    private String params;
    private String createBy;
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getResultIp() {
        return requestIp;
    }

    public void setResultIp(String resultIp) {
        this.requestIp = resultIp;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "SystemLog{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", method='" + method + '\'' +
                ", logType='" + logType + '\'' +
                ", requestIp='" + requestIp + '\'' +
                ", params='" + params + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
