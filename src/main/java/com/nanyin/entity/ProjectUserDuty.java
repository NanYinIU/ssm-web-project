package com.nanyin.entity;

import com.nanyin.entity.DO.RProjectUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "s_project_user_duty")
public class ProjectUserDuty extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 6061389509677961271L;
    /*
    在涉及多对多关系的时候，新建关联表放到DO下， 并使用OneToMany和ManyToOne进行关联映射
     */
    @OneToMany(mappedBy = "projectUserDuty")
    private Set<RProjectUser> rProjectUsers;
}
