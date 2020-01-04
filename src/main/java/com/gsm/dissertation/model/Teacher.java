package com.gsm.dissertation.model;


import com.gsm.dissertation.util.Sex;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 教师实体类
 */
@Entity
@Table(name = "teacher")
public class Teacher {
    /**
     * id 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;

    /**
     * 账号
     */
    @Column(length = 30,unique = true)
    @NotNull
    private String account;

    /**
     * 用户密码
     */
    @Column(length = 30)
    @NotNull
    private String password;

    /**
     * 用户姓名
     */
    @Column(length = 30)
    private String name;

    /**
     * 性别
     * 数据库中保村时并未使用男女来保存
     * 而是使用枚举的索引值
     * 男：0  女：1
     */
    private Sex grander;

    /**
     * 职称
     */
    private int titles;

    /**
     * 学历
     */
    private int education;

    /**
     * 研究方向
     */
    private String direction;

    /**
     * 学术成果
     */
    private String achievements;
}
