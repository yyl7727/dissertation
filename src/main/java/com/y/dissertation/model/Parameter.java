package com.y.dissertation.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 系统参数实体类
 */
@Entity
@Table(name = "parameter")
public class Parameter {
    /**
     * id 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;
    /**
     * 参数类型
     */
    @NotNull
    private String para_type;
    /**
     * 参数编号
     */
    @NotNull
    private String para_code;
    /**
     * 参数名称
     */
    @NotNull
    private String para_name;
    /**
     * 参数值
     */
    private String para_value;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPara_type() {
        return para_type;
    }

    public void setPara_type(String para_type) {
        this.para_type = para_type;
    }

    public String getPara_code() {
        return para_code;
    }

    public void setPara_code(String para_code) {
        this.para_code = para_code;
    }

    public String getPara_name() {
        return para_name;
    }

    public void setPara_name(String para_name) {
        this.para_name = para_name;
    }

    public String getPara_value() {
        return para_value;
    }

    public void setPara_value(String para_value) {
        this.para_value = para_value;
    }
}
