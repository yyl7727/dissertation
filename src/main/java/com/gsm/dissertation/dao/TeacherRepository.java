package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Integer> {

    Optional<Teacher> findTechByAccount(String account);


    void deleteTeacherByAccount(String account);
}
