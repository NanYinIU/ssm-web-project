package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = -7912979476697449896L;

    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64)
    private String name;

    @JSONField(serialize=false)
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

//    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_deleted",columnDefinition = "TINYINT(1)",nullable =
    false)
    private Short isDeleted;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;

    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "r_user_auth",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = @JoinColumn(name = "auth_id"))
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Auth> auths;

    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Project> projects;

    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<ProjectUserDuty> projectUserDuties;

    public User (int id){
        this.id = id;
    }

}
