package com.nanyin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Sex {
    private Integer id;
    private String name;
    private String comment;
    private Integer ord;
    private Short isDeleted;
    private Date gemCreate;
    private Date gemModify;
}
