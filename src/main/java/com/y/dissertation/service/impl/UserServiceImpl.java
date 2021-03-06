package com.y.dissertation.service.impl;

import com.y.dissertation.dao.TeacherRepository;
import com.y.dissertation.dao.UserRepository;
import com.y.dissertation.model.UserLogin;
import com.y.dissertation.model.Users;
import com.y.dissertation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public String save(Users users){
        try {
            userRepository.save(users);
            return "0";
        }catch (Exception ex){
            return "保存失败,错误信息:" + ex.getMessage();
        }
    }

    @Override
    public List<Users> findAll() {

        return userRepository.findAll();
    }

    @Override
    public Users findById(Integer uid) {
        return userRepository.findById(uid).get();
    }

    @Override
    @Transactional
    public String deleteByAccount(String account) {
        try {
            userRepository.deleteByAccount(account);
            return "0";
        }catch (Exception ex){
            return "删除失败,错误原因:" + ex.getMessage();
        }
    }

    /**
     * 检测登录用户是否存在
     * @param userLogin 登录实体包含用户名和密码
     * @return 登录返回信息 返回0时验证通过。
     */
    @Override
    public String checkUser(UserLogin userLogin) {
        Users u = new Users();
        Optional<Users> ou = userRepository.findStuByAccount(userLogin.getAccount());
        if (ou.isPresent()){
            u = ou.get();
            if(u.getPassword().equals(userLogin.getPassword())){
                return "0";
            }else {
                return "1";
            }
        }else{
            return "9";
        }
    }

    @Override
    public Users findUsersByAccount(String account) {
        try {
            return userRepository.findStuByAccount(account).get();
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
