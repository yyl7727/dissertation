package com.y.dissertation.service;

import com.y.dissertation.model.Parameter;

import java.util.List;

public interface ParameterService {
    /**
     * 获取某一类型的所有参数
     * @param type 参数类型
     * @return 对应类型所有系统参数
     */
    List<Parameter> getParameterByType(String type);

    /**
     * 获取所有用户类型
     * @return
     */
    List<Parameter> getAllUserType();
}
