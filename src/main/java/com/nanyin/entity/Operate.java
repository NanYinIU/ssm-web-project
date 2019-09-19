package com.nanyin.entity;

import com.nanyin.config.operateLog.OperationType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "operate" )
public class Operate extends BasicEntity implements Serializable {

    private static final long serialVersionUID = -450912372259672214L;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    @Column(length = 255)
    @Enumerated(EnumType.STRING)
    private OperationType operateType;

    @Column(length=255)
    private String ip;

    @Column(length = 255)
    private String device;
}
