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
     * 职位
     **/
    @Column(length = 255)
    private String position;

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
     * 手机号
     **/
    private String telephone;

    /**
     * 社交媒体账号
     **/
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @JoinTable(name = "r_person_social",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = @JoinColumn(name = "social_id"))
    private Set<SocialMedia> socialMedia;

    @Column(name = "is_deleted",columnDefinition = "TINYINT(4)")
    private Boolean isDeleted;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;



}
