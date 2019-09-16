package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_resource_icon")
public class ResourceIcon extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 7511944388442813343L;

}
