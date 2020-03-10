package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.TeacherRepository;
import com.gsm.dissertation.model.Teacher;
import com.gsm.dissertation.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher findTeacherByAccount(String account) {
        Teacher teacher = teacherRepository.findTechByAccount(account).get();
        return teacher;
    }

    /**
     * 检测登录教师是否存在
     * @param userLogin 登录实体包含用户名和密码
     * @return 登录返回信息 返回0时验证通过。
     */
    @Override
    public String checkTeacher(UserLogin userLogin) {
        Teacher teacher;
        Optional<Teacher> ou = teacherRepository.findTechByAccount(userLogin.getAccount());
        if (ou.isPresent()){
            teacher = ou.get();
            if(teacher.getPassword().equals(userLogin.getPassword())){
                return "0";
            }else {
                return "1";
            }
        }else{
            return "9";
        }
    }

    @Override
    public String update(Teacher teacher) {
        try {
            teacherRepository.saveAndFlush(teacher);
            return "0";
        }catch (Exception ex){
            return "保存失败，错误信息:"+ex.getMessage();
        }
    }
}
