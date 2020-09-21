package com.y.dissertation.model;

import javax.validation.constraints.NotNull;

public class UserLogin {
    @NotNull(message = "账号不能为空")
    private String account;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "登录类型不能为空")
    private String type;

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

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
