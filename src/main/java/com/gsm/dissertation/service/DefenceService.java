package com.gsm.dissertation.service;

import com.gsm.dissertation.model.Defence;

import java.util.List;

public interface DefenceService {
    List<Defence> findAllByTeacherAccount(String account);

    String updateResult(String account, String result);

    String save(Defence defence);

    Defence getDefenceById(Integer id);

    List<Defence> findAll();
}
