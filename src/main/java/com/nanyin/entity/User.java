package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Data
@Entity
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = -7912979476697449896L;
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;

    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private Set<Auth> auths;

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects;

    @ManyToMany(mappedBy = "users")
    private Set<ProjectUserDuty> projectUserDuties;


}
