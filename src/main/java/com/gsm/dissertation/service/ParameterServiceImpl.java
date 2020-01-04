package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.ParameterRepository;
import com.gsm.dissertation.model.Parameter;
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
}
