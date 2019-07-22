package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "resource")
public class Resource {
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

    @Column(length = 256)
    private String url;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "INT(11)",name = "icon_id")
    private ResourceIcon icon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "INT(11)",name = "type_id")
    private ResourceType type;

    @ManyToMany(mappedBy = "resources")
    private List<Auth> auths;
}
