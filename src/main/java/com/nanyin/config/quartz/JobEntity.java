package com.nanyin.config.quartz;

import lombok.Data;

import javax.persistence.*;
/**
 * Created by NanYin on 2019/9/18.
 */
@Data
@Entity
@Table(name = "job")
public class JobEntity {

    @Id
    @Column(columnDefinition = "INT(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_name")
    private String name;

    @Column(name = "job_group")
    private String group;

    @Column(name = "cron")
    private String cron;
    /*
    job的参数,jar包内需要的参数
     */
    @Column(name = "parameter")
    private String parameter;

    /*
    描述
     */
    private String description;
    /*
    VM参数
     */
    @Column(name = "vm_param")
    private String vmParam;
    /*
    包含jab的jar包路径
     */
    @Column(name = "jar_path")
    private String jarPath;
    /*
    job的状态
     */
    @Column(length = 32)
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    /*
     job的类
      */
    private String jobClass;
}
