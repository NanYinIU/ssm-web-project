package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 工单由 paper 组成，一个工单由若干页
 */
@Data
@Entity
public class Paper {

    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * Paper名称
     */
    @Column(length = 2048)
    String name;

    /**
     * Paper内容
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
     * 处理人
     */
    @OneToOne
    @JoinColumn
    User handler;

    /**
     * 处理时间
     */
    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtHandle;

    /**
     * 一个paper由多个task组成
     */
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "paper")
    Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    Tickets tickets;
}
