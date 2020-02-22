package com.nanyin.entity;

import com.nanyin.config.enums.OperationTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "operate" )
public class Operate implements Serializable {
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

    private static final long serialVersionUID = -450912372259672214L;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    @Column(length = 255)
    @Enumerated(EnumType.STRING)
    private OperationTypeEnum operateType;

    @Column(length=255)
    private String ip;

    @Column(length = 255)
    private String device;
}
