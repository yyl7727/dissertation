package com.gsm.dissertation.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 指导教师表实体类
 */
@Entity
@Table(name = "guideteacher")
public class GuideTeacher {
    /**
     * id 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;

    /**
     * 教师账号
     */
    @NotNull
    private String t_account;

    /**
     * 教师姓名
     */
    @NotNull
    private String t_name;

    /**
     * 学生账号
     */
    @NotNull
    private String s_account;

    /**
     * 学生姓名
     */
    @NotNull
    private String s_name;

    /**
     * 论文选题状态
     * 0：选题完毕 1：尚未选题
     */
    @NotNull
    private int topic_status;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getT_account() {
        return t_account;
    }

    public void setT_account(String t_account) {
        this.t_account = t_account;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getS_account() {
        return s_account;
    }

    public void setS_account(String s_account) {
        this.s_account = s_account;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public int getTopic_status() {
        return topic_status;
    }

    public void setTopic_status(int topic_status) {
        this.topic_status = topic_status;
    }
}
