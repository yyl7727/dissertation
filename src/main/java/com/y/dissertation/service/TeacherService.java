package com.y.dissertation.service;

import com.y.dissertation.model.Teacher;
import com.y.dissertation.model.UserLogin;

import java.util.List;

public interface TeacherService {

    Teacher findTeacherByAccount(String account);

    String checkTeacher(UserLogin userLogin);
    /**
     * 修改教师信息
     * @param teacher 教师实体
     * @return 是否成功
     */
    String update(Teacher teacher);

    List<Teacher> findAll();

    String deleteTeacherByAccount(String account);

    String save(Teacher teacher);
}
