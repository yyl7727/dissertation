package com.y.dissertation.service;

import com.y.dissertation.model.Defence;

import java.util.List;

public interface DefenceService {
    List<Defence> findAllByTeacherAccount(String account);

    String updateResult(String account, String result);

    String save(Defence defence);

    Defence getDefenceById(Integer id);

    List<Defence> findAll();
}
