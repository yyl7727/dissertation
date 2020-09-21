package com.y.dissertation.model;


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
    private Integer id;

    /**
     * 教师账号
     */
    @NotNull
    private String teacherAccount;

    /**
     * 教师姓名
     */
    @NotNull
    private String teacherName;

    /**
     * 学生账号
     */
    @NotNull
    private String studentAccount;

    /**
     * 学生姓名
     */
    @NotNull
    private String studentName;

    /**
     * 选择论题id
     */
    private String topicId;

    /**
     * 选择论题名称
     */
    private String topicName;

    /**
     * 论文选题状态
     * 0：选题完毕 1：尚未选题
     */
    @NotNull
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherAccount() {
        return teacherAccount;
    }

    public void setTeacherAccount(String teacherAccount) {
        this.teacherAccount = teacherAccount;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStudentAccount() {
        return studentAccount;
    }

    public void setStudentAccount(String studentAccount) {
        this.studentAccount = studentAccount;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
