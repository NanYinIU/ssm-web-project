package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 定义 工单，一个工单是一个工作内容的集合，比如一个项目
 */
@Data
@Entity
public class Tickets {
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 工单名称
     */
    @Column(length = 2048)
    String name;

    /**
     * 工单内容
     */
    @Column(columnDefinition = "TEXT")
    String comment;

    @Column(length = 11)
    Integer ord;

    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtCreate;

    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtModify;

    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtEnd;

    /**
     * 发起人
     */
    @OneToOne
    @JoinColumn(columnDefinition = "INT(11)")
    User sponsor;

    /**
     * 工单由页组成
     */
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tickets")
    Set<Paper> papers;

    /**
     * 优先级
     */
    @OneToOne
    @JoinColumn(columnDefinition = "INT(11)")
    Priority priority;

}
