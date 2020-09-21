package com.y.dissertation.service.impl;

import com.y.dissertation.dao.TeacherRepository;
import com.y.dissertation.model.Teacher;
import com.y.dissertation.model.UserLogin;
import com.y.dissertation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher findTeacherByAccount(String account) {
        Teacher teacher;
        try {
            teacher = teacherRepository.findTechByAccount(account).get();
        }catch (Exception ex){
            teacher = new Teacher();
        }
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

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teacherList = new ArrayList<>();
        try {
            teacherList = teacherRepository.findAll();
        }catch (Exception ex){

        }
        return teacherList;
    }

    @Override
    @Transactional
    public String deleteTeacherByAccount(String account) {
        try {
            teacherRepository.deleteTeacherByAccount(account);
            return "0";
        }catch (Exception ex){
            return "删除失败,错误信息:" + ex.getMessage();
        }
    }

    @Override
    public String save(Teacher teacher) {
        try {
            teacherRepository.save(teacher);
            return "0";
        }catch (Exception ex){
            return "添加失败,错误信息:" + ex.getMessage();
        }
    }
}
