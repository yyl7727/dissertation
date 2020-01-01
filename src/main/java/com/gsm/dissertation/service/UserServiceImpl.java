package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.UserRepository;
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
     * @param user 登录实体包含用户名和密码
     * @return True：用户存在  False：不存在
     */
    @Override
    public Users checkUser(UserLogin user) {
        Users u = null;
        Optional<Users> ou = userRepository.findByAccount(user.getAccount());
        if (ou.isPresent()){
            u = ou.get();
            if(u.getPassword().equals(user.getPassword())){
                return u;
            }
        }
        return null;
    }
}
