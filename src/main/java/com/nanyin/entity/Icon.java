package com.nanyin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "icon")
public class Icon extends BasicEntity implements Serializable {

    private static final long serialVersionUID = -3129897499335707182L;

    @Column(name = "url",length = 255)
    private String url;

    @Column(length = 255)
    private String className;


}
