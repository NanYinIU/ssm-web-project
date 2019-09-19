package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "unit")
public class Unit implements Serializable {
    private static final long serialVersionUID = 7358059864252304298L;
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64)
    private String name;

//    @JSONField(serialize=false)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Unit parentId;

    @Column(length = 11)
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private UnitCategory unitCategory;

    @Column(length = 11)
    private Integer ord;

    @Column(name = "is_deleted",columnDefinition = "TINYINT(4)")
    private Boolean isDeleted;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;
}
