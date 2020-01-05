package com.gsm.dissertation.service;


import com.gsm.dissertation.model.GuideTeacher;

import java.util.List;

public interface GuideTeacherService {
    /**
     * 根据教师账号获取对应指导教师下的所有学生
     * @param account 教师账号
     * @return 对应指导教师下的所有学生
     */
    List<GuideTeacher> getGuideTeacherByAccount(String account);
}
