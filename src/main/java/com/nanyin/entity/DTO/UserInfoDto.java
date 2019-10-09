package com.nanyin.entity.DTO;

import com.nanyin.config.util.Copyable;
import com.nanyin.entity.Sex;
import com.nanyin.entity.User;
import lombok.Data;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;


@Data
public class UserInfoDto {

    private int id;
//    private String username;

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

}
