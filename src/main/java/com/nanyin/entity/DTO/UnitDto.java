package com.nanyin.entity.DTO;

import com.nanyin.entity.Unit;
import com.nanyin.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class UnitDto {
    private Integer id;
    private String name;

    private String address;
    private String comment;

    Set<User> users;
}
