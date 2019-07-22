package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "s_project_level")
public class ProjectLevel {
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

}
