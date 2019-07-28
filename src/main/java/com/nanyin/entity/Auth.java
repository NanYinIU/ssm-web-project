package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth")
public class Auth implements Serializable {

    private static final long serialVersionUID = 4541485813650085417L;
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.AUTO)
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


    @org.springframework.data.annotation.Transient
    @ManyToMany
    @JSONField(serialize = false)
    @JoinTable(name = "r_user_auth",
            joinColumns = {@JoinColumn(name = "auth_id")},
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> users;

    @ManyToMany
    @JSONField(serialize = false)
    @JoinTable(name = "r_resource_auth",
            joinColumns = {@JoinColumn(name = "auth_id")},
            inverseJoinColumns = @JoinColumn(name = "resources_id"))
    private List<Resource> resources;

}
