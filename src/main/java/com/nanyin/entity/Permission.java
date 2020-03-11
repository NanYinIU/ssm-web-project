package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission{
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64)
    String name;
    @Column(length = 1024)
    String comment;
    @Column(length = 11)
    Integer ord;
    @Column(name = "is_deleted",columnDefinition = "TINYINT(4)")
    Boolean isDeleted=false;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtCreate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtModify;

    @JSONField(serialize=false)
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "r_role_permission",
            joinColumns = {@JoinColumn(name = "permission_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    Set<Role> roles;

}
