package com.gsm.dissertation.service;

import com.gsm.dissertation.model.UserLogin;
import com.gsm.dissertation.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {

    String save(Users users);

    List<Users> findAll();

    Users findById(Integer uid);

    String deleteByAccount(String account);

    String checkUser(UserLogin userLogin);

    Users findUsersByAccount(String account);

    String update(Users student);
}
