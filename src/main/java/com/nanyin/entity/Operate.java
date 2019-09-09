package com.nanyin.entity;

import com.nanyin.config.operateLog.OperationType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "operate" )
public class Operate {

    private static final long serialVersionUID = -450912372259672214L;
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64)
    private String name;

    @Column(length = 1024)
    private String comment;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    @Column(length = 255)
    @Enumerated(EnumType.STRING)
    private OperationType operateType;

    @Column(length=255)
    private String ip;

    @Column(length = 255)
    private String device;
}
