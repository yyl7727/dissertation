package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.TeacherRepository;
import com.gsm.dissertation.dao.UserRepository;
import com.gsm.dissertation.model.Teacher;
import com.gsm.dissertation.model.UserLogin;
import com.gsm.dissertation.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public void save(Users users) throws Exception {
        try {
            userRepository.save(users);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public Page<Users> findAll(String kw, Pageable pageable) {
        return userRepository.findByKeyword(kw, pageable);
    }

    @Override
    public Users findById(Integer uid) {
        return userRepository.findById(uid).get();
    }

    @Override
    public void delete(Users users) {
        userRepository.delete(users);
    }

    @Override
    public void deleteById(Integer uid) {
        userRepository.deleteById(uid);
    }

    @Override
    @Transactional
    public void deletes(List<Users> usersList) {
        for(Users user : usersList){
            userRepository.delete(user);
        }
    }

    /**
     * 检测登录用户是否存在
     * @param userLogin 登录实体包含用户名和密码
     * @return 登录返回信息 返回0时验证通过。
     */
    @Override
    public String checkUser(UserLogin userLogin) {
        Users u = null;
        Optional<Users> ou = userRepository.findStuByAccount(userLogin.getAccount());
        if (ou.isPresent()){
            u = ou.get();
            if(u.getPassword().equals(userLogin.getPassword())){
                return "0";
            }else {
                return "密码错误";
            }
        }else{
            return "用户不存在";
        }
    }

    @Override
    public Users findUsersByAccount(String account) {
        try {
            Users student = userRepository.findStuByAccount(account).get();
            return student;
        }catch (Exception ex){
            return new Users();
        }
    }

    @Override
    public String update(Users student) {
        try {
            userRepository.saveAndFlush(student);
            return "0";
        }catch (Exception ex){
            return "保存失败，错误信息:"+ex.getMessage();
        }
    }
}
