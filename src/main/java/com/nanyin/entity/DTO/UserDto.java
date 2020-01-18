package com.nanyin.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@ApiModel(value="UserDto",description = "用户传输层")
public class UserDto {
    @ApiModelProperty(name = "唯一标识",notes = "Uniquely identifies")
    private int id;
    @ApiModelProperty(name = "用户名",notes = "User Name")
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


}
