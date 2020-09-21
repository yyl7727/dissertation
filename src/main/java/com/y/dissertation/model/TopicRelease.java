package com.y.dissertation.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 论文选题实体类
 */
@Entity
@Table(name = "topicrelease")
public class TopicRelease {
    /**
     * id 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;
    /**
     * 论题名称
     */
    @NotNull(message = "论题名称不能为空")
    private String topicName;

    /**
     * 论题说明
     */
    @NotNull(message = "论题说明不能为空")
    private String topicExplain;

    /**
     * 论题发布教师
     */
    @NotNull(message = "论题发布教师不能为空")
    private String teacherAccount;

    /**
     * 教师姓名
     */
    @NotNull(message = "论题发布教师姓名不能为空")
    private String teacherName;

    /**
     * 可选专业
     */
    private String topicMajor;

    /**
     * 已选择此课题的人数
     */
    private int studentCount;

    /**
     * 发布状态
     * 0有效  1失效
     */
    @NotNull(message = "论题状态不能为空")
    private String t_status;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicExplain() {
        return topicExplain;
    }

    public void setTopicExplain(String topicExplain) {
        this.topicExplain = topicExplain;
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

    public String getTopicMajor() {
        return topicMajor;
    }

    public void setTopicMajor(String topicMajor) {
        this.topicMajor = topicMajor;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }
}
