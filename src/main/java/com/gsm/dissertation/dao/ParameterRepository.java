package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParameterRepository extends JpaRepository<Parameter,Integer> {

    @Query("select p from Parameter p where p.para_type=?1")
    List<Parameter> getParametersByType(String type);
}
