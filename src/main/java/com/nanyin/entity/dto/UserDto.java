package com.nanyin.entity.dto;

import com.nanyin.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
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
    private String password;

    private short age;

    private int sex;

    private int unit;

    private int status;

    private Date gmtCreate;

    private Date gmtModify;

    private int[] roles;

    private int[] auths;

    private int[] projects;

    private int[] projectUserDuties;

}
