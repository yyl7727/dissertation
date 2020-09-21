package com.y.dissertation.dao;

import com.y.dissertation.model.GuideTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuideTeacherRepository extends JpaRepository<GuideTeacher,Integer> {

    /**
     * 根据教师账号获取对应指导教师下的所有学生
     * @param account 教师账号
     * @return 对应指导教师下的所有学生1
     */
    @Query("select g from GuideTeacher g where g.teacherAccount=?1")
    List<GuideTeacher> getGuideTeacherByAccount(String account);

    @Query("select g from GuideTeacher g where g.studentAccount=?1")
    List<GuideTeacher> getGuideTeacherByStudentAccount(String account);

    @Query("select g from GuideTeacher g")
    List<GuideTeacher> findAll();
}
