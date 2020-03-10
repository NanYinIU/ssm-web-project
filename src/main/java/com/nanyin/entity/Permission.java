package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
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

    @ManyToMany(mappedBy = "permissions",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<Role> roles;

}
