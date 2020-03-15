package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.IntendantRepository;
import com.gsm.dissertation.model.Intendant;
import com.gsm.dissertation.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IntendantServiceImpl implements IntendantService {
    @Autowired
    private IntendantRepository intendantRepository;

    @Override
    public String checkIntendant(UserLogin userLogin) {
        Intendant intendant;
        int checkFlag = intendantRepository.checkIntendant(userLogin.getAccount());

        if (checkFlag > 0){
            intendant = intendantRepository.findByAccount(userLogin.getAccount()).get();
            if (userLogin.getPassword().equals(intendant.getPassword())){
                return "0";
            }else {
                return "1";
            }
        }else {
            return "9";
        }
    }

    @Override
    public Intendant findByAccount(String account) {
        return intendantRepository.findByAccount(account).get();
    }

    @Override
    public String update(Intendant intendant) {
        try {
            intendantRepository.saveAndFlush(intendant);
            return "0";
        }catch (Exception ex){
            return "保存失败，错误信息:"+ex.getMessage();
        }
    }
}
