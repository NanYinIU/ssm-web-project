package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 工单下的一页中有多个任务组成
 */
@Data
@Entity
public class Task {
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * Task名称
     */
    @Column(length = 2048)
    String name;

    /**
     * Task内容
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

    @OneToOne
    @JoinColumn
    Priority priority;

    @ManyToOne
    @JoinColumn(name = "paper_id")
    Paper paper;

}
