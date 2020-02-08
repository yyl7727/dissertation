package com.gsm.dissertation.model;

import com.gsm.dissertation.util.Sex;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {
    /**
     * id 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

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
     * 出生日期
     */
    private LocalDateTime birthday;

    /**
     * 手机号码
     */
    @Column(length = 11)
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 最后登录时间
     */
    private String lasttime;

    /**
     * 专业
     */
    @NotNull
    private String major;

    /**
     * 是否有效
     * 0:无效   1：有效
     */
    private Integer validstate = 1;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) { this.uid = uid; }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getGrander() {
        return grander;
    }

    public void setGrander(Sex grander) {
        this.grander = grander;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getValidstate() {
        return validstate;
    }

    public void setValidstate(Integer validstate) {
        this.validstate = validstate;
    }
}
