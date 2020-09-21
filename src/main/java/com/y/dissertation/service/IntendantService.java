package com.y.dissertation.service;

import com.y.dissertation.model.Intendant;
import com.y.dissertation.model.UserLogin;

public interface IntendantService {
    String checkIntendant(UserLogin userLogin);

    Intendant findByAccount(String account);

    String update(Intendant intendant);
}
