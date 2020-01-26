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
    private String teacher_account;

    /**
     * 教师姓名
     */
    @NotNull
    private String teacher_name;

    /**
     * 学生账号
     */
    @NotNull
    private String student_account;

    /**
     * 学生姓名
     */
    @NotNull
    private String student_name;

    /**
     * 选择论题id
     */
    private int topic_id;

    /**
     * 选择论题名称
     */
    private String topic_name;

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

    public String getTeacher_account() {
        return teacher_account;
    }

    public void setTeacher_account(String teacher_account) {
        this.teacher_account = teacher_account;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getStudent_account() {
        return student_account;
    }

    public void setStudent_account(String student_account) {
        this.student_account = student_account;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public int getTopic_status() {
        return topic_status;
    }

    public void setTopic_status(int topic_status) {
        this.topic_status = topic_status;
    }
}
