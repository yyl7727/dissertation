package com.y.dissertation.util;

import com.y.dissertation.model.Parameter;
import com.y.dissertation.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ParameterUtil {
    @Autowired
    ParameterService parameterService;

    public List<Parameter> getAllUserType(){
        List<Parameter> userTypeList = parameterService.getAllUserType();
        return userTypeList;
    }
}
