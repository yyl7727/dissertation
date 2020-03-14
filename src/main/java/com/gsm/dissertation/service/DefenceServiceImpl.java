package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.DefenceRepository;
import com.gsm.dissertation.model.Defence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefenceServiceImpl implements DefenceService {
    @Autowired
    private DefenceRepository defenceRepository;

    @Override
    public List<Defence> findAllByTeacherAccount(String account) {
        return defenceRepository.findAllByTeacherAccount(account);
    }

    @Transactional
    @Override
    public String updateResult(String account, String result) {
        try {
            defenceRepository.updateResult(account, result);
            return "0";
        }catch (Exception ex){
            return "更改成绩失败,错误信息:" + ex.getMessage();
        }

    }

    @Override
    public String save(Defence defence) {
        try {
            defenceRepository.save(defence);
            return "0";
        }catch (Exception ex){
            return "保存失败,错误信息:" + ex.getMessage();
        }

    }

    @Override
    public Defence getDefenceById(Integer id) {
        Defence defence;
        try {
            Optional<Defence> defenceOptional = defenceRepository.getById(id);
            defence = defenceOptional.orElseGet(Defence::new);
            return defence;
        }catch (Exception ex){
            return defence = new Defence();
        }
    }
}
