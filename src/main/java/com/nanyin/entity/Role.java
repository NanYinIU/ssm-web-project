package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.shiro.authz.SimpleRole;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "role")
public class Role {
    private static final long serialVersionUID = 8538788781327321942L;
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
//    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtModify;

    @JSONField(serialize = false)
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "r_user_role",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users;


    @JSONField(serialize = false)
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "r_role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
