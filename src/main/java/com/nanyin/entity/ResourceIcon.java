package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_resource_icon")
public class ResourceIcon implements Serializable {
    private static final long serialVersionUID = 7511944388442813343L;
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    private Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;
}
