package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resource")
public class Resource extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 4850603363229927336L;

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
