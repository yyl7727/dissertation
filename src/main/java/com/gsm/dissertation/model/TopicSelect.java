package com.gsm.dissertation.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "topicselect")
public class TopicSelect {
    /**
     * id 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 论题id
     */
    @NotNull
    private String tId;

    /**
     * 论题名称
     */
    private String tName;
    /**
     * 学生id
     */
    private String sId;
    /**
     * 学生姓名
     */
    private String sName;

    /**
     * 教师编号
     */
    private String t_TId;

    /**
     * 教师姓名
     */
    private String t_TName;

    /**
     * 申请时间
     */
    private String sTime;

    /**
     * 状态 0：待审批 1：通过申请 2：未通过
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getT_TId() {
        return t_TId;
    }

    public void setT_TId(String t_TId) {
        this.t_TId = t_TId;
    }

    public String getT_TName() {
        return t_TName;
    }

    public void setT_TName(String t_TName) {
        this.t_TName = t_TName;
    }
}
