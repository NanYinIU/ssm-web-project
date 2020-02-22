package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 作为人员的扩展信息使用
 * 如职位、头像、各种联系方式
 **/
@Data
@Entity
@Table(name = "person")
public class Person implements Serializable {
    private static final long serialVersionUID = -7783058473676963173L;
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY )
    private Integer id;

    @Column(length = 64)
    private String name;

    /**
     * 居住地址
     **/
    @Column(length = 512)
    private String address;

    /**
     * 头像
     **/
    @Column(length = 512)
    private String avatar;

    /**
     * 简介
     **/
    @Column(length = 512)
    private String introduction;

    /**
     * 手机号
     **/
    private String telephone;

    @Column(name = "is_deleted",columnDefinition = "TINYINT(4) default 0")
    private Boolean isDeleted ;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;


}
