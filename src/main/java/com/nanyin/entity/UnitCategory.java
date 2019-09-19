package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "s_unit_category")
public class UnitCategory extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 4605177010744033641L;
}
