package com.gsm.dissertation.model;

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
    private String t_topicname;

    /**
     * 论题说明
     */
    @NotNull(message = "论题说明不能为空")
    private String t_explain;

    /**
     * 论题发布教师
     */
    @NotNull(message = "论题发布教师不能为空")
    private String t_teacher;

    /**
     * 教师姓名
     */
    @NotNull(message = "论题发布教师姓名不能为空")
    private String t_name;

    /**
     * 发布状态
     * 0有效  1失效
     */
    @NotNull(message = "论题状态不能为空")
    private Integer t_status;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getT_topicname() {
        return t_topicname;
    }

    public void setT_topicname(String t_topicname) {
        this.t_topicname = t_topicname;
    }

    public String getT_explain() {
        return t_explain;
    }

    public void setT_explain(String t_explain) {
        this.t_explain = t_explain;
    }

    public String getT_teacher() {
        return t_teacher;
    }

    public void setT_teacher(String t_teacher) {
        this.t_teacher = t_teacher;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public Integer getT_status() {
        return t_status;
    }

    public void setT_status(Integer t_status) {
        this.t_status = t_status;
    }
}
