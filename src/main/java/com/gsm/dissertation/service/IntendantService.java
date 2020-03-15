package com.gsm.dissertation.service;

import com.gsm.dissertation.model.Intendant;
import com.gsm.dissertation.model.UserLogin;

public interface IntendantService {
    String checkIntendant(UserLogin userLogin);

    Intendant findByAccount(String account);

    String update(Intendant intendant);
}
