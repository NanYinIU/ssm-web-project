package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
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

    @JSONField(name = "id")
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @JSONField(name = "name")
    @Column(length = 64)
    private String name;

    @Size(min = 3,max = 63,message ="{user_password_length}" )
//    @JSONField(serialize=false)
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
    @JSONField(name = "unit")
    @JoinColumn(columnDefinition = "INT(11)",name = "unit_id")
    private Unit unit;

    @OneToOne()
    @JSONField(name = "status")
    @JoinColumn(columnDefinition = "INT(11)",name = "status_id")
    private Status status;

    @Column(name = "is_deleted",columnDefinition = "TINYINT(1)",nullable =
    false)
    private Short isDeleted;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;

    @JSONField(name = "roles")
    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private Set<Role> roles;

    /**
     * manytomany 通过用户设置角色，所以用户user
     *  为主表，添加JoinTable注解，auth表需要添加mapby
     *  不能添加 cascade(CascadeType.ALL)，删除的时候会对从表auth
     *  同样进行删除。
     **/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "r_user_auth",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = @JoinColumn(name = "auth_id"))
    @NotFound(action = NotFoundAction.IGNORE)
    @JSONField(name = "auths")
    private Set<Auth> auths;

    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private Set<Project> projects;

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "r_project_user",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = @JoinColumn(name = "duty_id"))
    private Set<ProjectUserDuty> projectUserDuties;

    @OneToOne
    @JoinColumn(columnDefinition = "INT(11)",name = "person_id")
    private Person person;

    public User (int id){
        this.id = id;
    }

}
