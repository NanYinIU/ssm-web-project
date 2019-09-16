package com.nanyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth")
public class Auth extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 4541485813650085417L;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "auths")
    @JSONField(serialize = false)
    private Set<User> users = new HashSet<>();

    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "auths")
    private Set<Resource> resources;


}
