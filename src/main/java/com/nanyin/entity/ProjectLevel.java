package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "s_project_level")
public class ProjectLevel extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 1842831405020383194L;

}
