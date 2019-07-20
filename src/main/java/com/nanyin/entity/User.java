package com.nanyin.entity;

import com.nanyin.enumEntity.StatusEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String salt;
    private Short age;
    private Integer sex;
    private Integer unit;
    private Integer status;
    private Short isDeleted;
    private Date gemCreate;
    private Date gemModify;

    private List<String> roles;
    private List<String> auths;
}
