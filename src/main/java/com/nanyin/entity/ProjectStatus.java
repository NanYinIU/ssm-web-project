package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_project_status")
public class ProjectStatus extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 6334887949207934312L;

}
