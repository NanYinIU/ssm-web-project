package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(length = 64)
    private String name;
//    @Transient
    @Column(length = 64)
    private String password;
    @Column(length = 64)
    private String email;
    @Column(length = 64)
    private String salt;

    @Column(columnDefinition = "TINYINT(4)")
    private Short age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="sex_id",columnDefinition = "INT(11)")
    private Sex sex;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "INT(11)",name = "unit_id")
    private Unit unit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "INT(11)",name = "status_id")
    private Status status;

    @Column(name = "is_deleted",columnDefinition = "TINYINT(4)")
    private Short isDeleted;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;

    @ManyToMany(mappedBy = "users")
    private List<Role> roles;

    @ManyToMany(mappedBy = "users")
    private List<Auth> auths;

    @ManyToMany(mappedBy = "users")
    private List<Project> projects;

    @ManyToMany(mappedBy = "users")
    private List<ProjectUserDuty> projectUserDuties;
}
