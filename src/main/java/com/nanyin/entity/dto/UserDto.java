package com.nanyin.entity.dto;

import com.nanyin.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {

    private int id;
    private String name;
    private String email;

    private short age;

    private Sex sex;

    private Unit unit;

    private Status status;

    private Date gmtCreate;

    private Date gmtModify;

    private Collection<Role> roles;
//
    private Collection<Auth> auths;
//
    private Collection<Project> projects;
//
    private Collection<ProjectUserDuty> projectUserDuties;

}
