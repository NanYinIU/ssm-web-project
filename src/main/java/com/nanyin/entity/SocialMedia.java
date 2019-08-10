package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class SocialMedia implements Serializable {

    private static final long serialVersionUID = 8781157236787201134L;
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64)
    private String name;
    @Column(length = 1024)
    private String comment;
    @Column(length = 11)
    private Integer ord;
    @Column(name = "is_deleted",columnDefinition = "TINYINT(4)")
    private Boolean isDeleted;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;

    @Column(length = 255)
    private String url;

//    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "socialMedia")
//    @JSONField(serialize = false)
//    private Set<Person> person = new HashSet<>();

    @JoinColumn(name = "social_type_id")
    @OneToOne
    private SocialType socialType;

}
