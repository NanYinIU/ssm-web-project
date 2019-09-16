package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "s_user_status")
public class Status extends BasicEntity implements Serializable{
    private static final long serialVersionUID = -866998743428907531L;
}
