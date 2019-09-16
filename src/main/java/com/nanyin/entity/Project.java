package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project implements Serializable {
    private static final long serialVersionUID = -5380603783848286331L;
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(length = 512)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Project parentId;

    @Column(length = 11)
    private Integer ord;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    private ProjectStatus status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private ProjectType type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "level_id")
    private ProjectLevel level;
    @Column(length = 512)
    private String code;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date planStart;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date planFinish;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date actualStart;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date actualFinish;

    @Column(name = "is_deleted",columnDefinition = "TINYINT(4)")
    private Boolean isDeleted;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtCreate;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date gmtModify;

    /**
     * 如何正确的、更好的使用ManyToMany注解？
     * - 有几个知识点需要注意 1. cascade 使用cascade标志为级联，入下面的PERSIST为级联保存 MERGE为级联修改
     *     如果使用双向关联，则必须使用添加/删除实用程序方法，以便确保关联的两端同步。
     * - 2. NaturalId 这个注解可以提供类似@Id的作用，在该实体类中表示唯一、非空，也可理解为能够唯一标志一个实体类
     *     同样可根据这个NaturalId标识的属性查出唯一的entity
     * - 3.mappedBy 属性。一对关联关系只有一方能够管理，拥有这个主导权，并且更改仅从此特定方传播到数据库，
     *     也就是说查找、修改都需要从project入手
     */
    @JSONField(serialize = false)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "r_project_user"
            ,joinColumns = {@JoinColumn(name = "project_id")}
            , inverseJoinColumns = {@JoinColumn(name = "users_id")})
    private Set<User> users;

}
