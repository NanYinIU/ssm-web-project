package com.nanyin.entity.dto;

import com.nanyin.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String name;
    private String email;

    private Short age;

    private Sex sex;

    private Unit unit;

    private Status status;


    private Date gmtCreate;

    private Date gmtModify;

//    private List<Role> roles;
//
//    private List<Auth> auths;
//
//    private List<Project> projects;
//
//    private List<ProjectUserDuty> projectUserDuties;


}
