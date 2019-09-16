package com.nanyin.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_resource_type")
public class ResourceType extends BasicEntity implements Serializable {
    private static final long serialVersionUID = -8746707621527742936L;

}
