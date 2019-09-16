package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_project_type")
public class ProjectType extends BasicEntity implements Serializable {
    private static final long serialVersionUID = -8866225307710726408L;

}
