package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 单位表
 *
 */
@Getter
@Setter
@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(length = 256)
    String name;

    /**
     * 编号
     */
    @Column(length = 256)
    String code;

    /**
     * 地址
     */
    @Column(length = 2048)
    String address;

    /**
     * 内容
     */
    @Column(columnDefinition = "TEXT")
    String comment;

    @Column(length = 11)
    Integer ord;

    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtCreate;

    @Temporal(value=TemporalType.TIMESTAMP)
    Date gmtModify;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    Set<Unit> children;

    @ManyToOne()
    @JoinColumn(name = "parent_id")
    @JSONField(serialize = false)
    Unit parent;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "unit")
    Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equal(id, unit.id) &&
                Objects.equal(name, unit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, code, address, comment, ord, gmtCreate, gmtModify, children, parent, users);
    }
}
