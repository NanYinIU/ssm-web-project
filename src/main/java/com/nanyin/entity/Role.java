package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {
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
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "r_user_role",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "users_id")})
    private List<User> users;

}
