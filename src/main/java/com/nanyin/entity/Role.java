package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 8538788781327321942L;

    @JSONField(serialize = false)
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "r_user_role",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "users_id")})
    private List<User> users;

}
