package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "auth")
public class Auth {
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
    private Date gemCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gemModify;

    @ManyToMany
    @JoinTable(name = "r_user_auth",
            joinColumns = {@JoinColumn(name = "auth_id")})
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "r_resource_auth",
            joinColumns = {@JoinColumn(name = "auth_id")})
    private List<Resource> resources;
}
