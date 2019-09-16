package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_project_user_duty")
public class ProjectUserDuty extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 6061389509677961271L;

//    @JSONField(serialize = false)
//    @ManyToMany(mappedBy = "projectUserDuties",fetch = FetchType.EAGER)
//    private List<User> users;
}
