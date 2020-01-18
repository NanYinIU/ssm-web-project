package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by NanYin on 2019/9/16.
 * 标准数据表，用于展示基本的表中通用的数据
 */
@MappedSuperclass
@Data
public class BasicEntity {


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
    Boolean isDeleted;
    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtModify;
}
