package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 工作处理情况
 */
@Data
@Entity
public class HandleStatus {

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
    private static final long serialVersionUID = -866998743428907531L;
}
