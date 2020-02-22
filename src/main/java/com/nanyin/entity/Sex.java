package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "s_user_sex")
public class Sex implements Serializable {
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
    @Column(name = "is_deleted",columnDefinition = "TINYINT(4) default 0")
    Boolean isDeleted;
    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtModify;

    public Sex() {
    }

    private static final long serialVersionUID = -450912372259672214L;
}
