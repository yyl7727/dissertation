package com.gsm.dissertation.model;

import javax.persistence.*;

@Entity
@Table(name = "notice")
public class Notice {
    /**
     * id 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 发送方账号
     */
    private String sendUserAccount;

    /**
     * 发送方姓名
     */
    private String sendUserName;

    /**
     * 接收方账号
     */
    private String getUserAccount;

    /**
     * 接收方姓名
     */
    private String getUserName;

    /**
     * 发送主题
     */
    private String sendSubject;

    /**
     * 发送内容
     */
    private String sendContent;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 状态 0：未读  1：已读
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSendUserAccount() {
        return sendUserAccount;
    }

    public void setSendUserAccount(String sendUserAccount) {
        this.sendUserAccount = sendUserAccount;
    }

    public String getGetUserAccount() {
        return getUserAccount;
    }

    public void setGetUserAccount(String getUserAccount) {
        this.getUserAccount = getUserAccount;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getGetUserName() {
        return getUserName;
    }

    public void setGetUserName(String getUserName) {
        this.getUserName = getUserName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getSendSubject() {
        return sendSubject;
    }

    public void setSendSubject(String sendSubject) {
        this.sendSubject = sendSubject;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
