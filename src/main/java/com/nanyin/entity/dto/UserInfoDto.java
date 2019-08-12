package com.nanyin.entity.dto;

import lombok.Data;

import java.util.Date;


@Data
public class UserInfoDto {

    private int id;
//    private String name;

    private String email;

    private String password;

    private short age;

    private int sex;

    private int unit;

    private int status;

    private Date gmtCreate;

    private Date gmtModify;

    /**
     * person 中的属性
     **/

    private String position;

    private String address;

    private String name;

    private String[] socialMedia;
}
