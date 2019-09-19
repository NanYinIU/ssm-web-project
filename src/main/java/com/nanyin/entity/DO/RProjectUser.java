package com.nanyin.entity.DO;

import com.nanyin.entity.Project;
import com.nanyin.entity.ProjectUserDuty;
import com.nanyin.entity.User;

import javax.persistence.*;

/**
 * Created by NanYin on 2019/9/16.
 * 关联表
 */
@Entity
@Table(name = "r_project_user")
public class RProjectUser {
    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "duty_id")
    private ProjectUserDuty projectUserDuty;
}
