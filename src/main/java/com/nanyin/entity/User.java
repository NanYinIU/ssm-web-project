package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name="user")
@ApiModel(value="User",description = "用户实体类")
public class User implements Serializable {

    private static final long serialVersionUID = -7912979476697449896L;

    @JSONField(name = "id")
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;


    @JSONField(name = "name")
    @Column(length = 64)
    private String name;

    @Size(min = 3,max = 63,message ="{user_password_length}" )
    @JSONField(name = "password")
    @Column(length = 64)
    private String password;

    @JSONField(name = "email")
    @Email(message = "{user_email_format}")
    @Column(length = 64)
    private String email;

    @Column(length = 64)
    private String salt;

    @Column(columnDefinition = "TINYINT(4)")
    private Short age;

    @OneToOne()
    @JSONField(name = "sex")
    @JoinColumn(name="sex_id",columnDefinition = "INT(11)")
    private Sex sex;

    @OneToOne()
    @JSONField(name = "status")
    @JoinColumn(columnDefinition = "INT(11)",name = "status_id")
    private Status status;

    @Column(name = "is_deleted",columnDefinition = "TINYINT(1)",nullable =
    false)
    private Short isDeleted=0;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;

    @JSONField(name = "roles")
    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "INT(11)",name = "person_id")
    private Person person;

}
