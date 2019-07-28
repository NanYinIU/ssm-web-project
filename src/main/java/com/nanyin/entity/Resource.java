package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Table(name = "resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 4850603363229927336L;
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
    private Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;

    @Column(length = 256)
    private String url;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "INT(11)",name = "icon_id")
    private ResourceIcon icon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition = "INT(11)",name = "type_id")
    private ResourceType type;

    @ManyToMany(cascade = CascadeType.ALL)
//    @JSONField(serialize = false)
    @JoinTable(name = "r_resource_auth",
            joinColumns = {@JoinColumn(name = "resources_id")},
            inverseJoinColumns = @JoinColumn(name = "auth_id"))
    private Set<Auth> auths = new HashSet<>();

}
