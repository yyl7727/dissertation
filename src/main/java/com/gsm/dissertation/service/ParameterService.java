package com.gsm.dissertation.service;

import com.gsm.dissertation.model.Parameter;

import java.util.List;

public interface ParameterService {
    /**
     * 获取某一类型的所有参数
     * @param type 参数类型
     * @return 对应类型所有系统参数
     */
    List<Parameter> getParameterByType(String type);
}
