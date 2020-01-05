package com.gsm.dissertation.service;

import com.gsm.dissertation.model.Teacher;
import com.gsm.dissertation.model.UserLogin;
import com.gsm.dissertation.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {

    void save(Users users) throws Exception;

    Page<Users> findAll(String kw, Pageable pageable);

    Users findById(Integer uid);

    void delete(Users users);

    void deleteById(Integer uid);

    void deletes(List<Users> usersList);

    String checkUser(UserLogin userLogin);

    Teacher findTeacherByAccount(String account);

    String checkTeacher(UserLogin userLogin);
}
