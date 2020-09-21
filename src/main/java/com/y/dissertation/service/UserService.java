package com.y.dissertation.service;

import com.y.dissertation.model.UserLogin;
import com.y.dissertation.model.Users;


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
