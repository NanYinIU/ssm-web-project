package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "s_user_sex")
public class Sex extends BasicEntity implements Serializable {
    private static final long serialVersionUID = -450912372259672214L;
}
