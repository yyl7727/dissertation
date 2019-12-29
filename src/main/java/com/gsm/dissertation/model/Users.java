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
    @Column(length = 30)
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
    private Integer lasttime;

    /**
     * 登录次数
     */
    private Integer logincount;

    /**
     * 是否有效
     */
    private Integer validstate;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

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

    public Integer getLasttime() {
        return lasttime;
    }

    public void setLasttime(Integer lasttime) {
        this.lasttime = lasttime;
    }

    public Integer getLogincount() {
        return logincount;
    }

    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    public Integer getValidstate() {
        return validstate;
    }

    public void setValidstate(Integer validstate) {
        this.validstate = validstate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(uid, users.uid) &&
                Objects.equals(account, users.account) &&
                Objects.equals(password, users.password) &&
                Objects.equals(name, users.name) &&
                grander == users.grander &&
                Objects.equals(birthday, users.birthday) &&
                Objects.equals(mobile, users.mobile) &&
                Objects.equals(email, users.email) &&
                Objects.equals(lasttime, users.lasttime) &&
                Objects.equals(logincount, users.logincount) &&
                Objects.equals(validstate, users.validstate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, account, password, name, grander, birthday, mobile, email, lasttime, logincount, validstate);
    }
}
