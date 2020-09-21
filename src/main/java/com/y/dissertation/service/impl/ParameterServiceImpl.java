package com.y.dissertation.service.impl;

import com.y.dissertation.dao.ParameterRepository;
import com.y.dissertation.model.Parameter;
import com.y.dissertation.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterServiceImpl implements ParameterService {
    @Autowired
    private ParameterRepository parameterRepository;

    /**
     * 获取对应类型的参数list
     * @param type 参数类型
     * @return 对应类型的参数list
     */
    @Override
    public List<Parameter> getParameterByType(String type) {
        List<Parameter> list_Parameter = parameterRepository.getParametersByType(type);
        return list_Parameter;
    }

    /**
     * 获取所有用户类型
     * @return
     */
    @Override
    public List<Parameter> getAllUserType() {
        List<Parameter> userTypeList = parameterRepository.getAllUserType();
        return userTypeList;
    }
}
