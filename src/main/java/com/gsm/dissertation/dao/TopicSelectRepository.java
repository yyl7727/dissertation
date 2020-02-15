package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.TopicSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicSelectRepository extends JpaRepository<TopicSelect, Integer> {
    @Query("select count(t) from TopicSelect t where t.studentAccount=?1")
    Integer findCountByStudent(String account);

    @Query("select t from TopicSelect t where t.teacherAccount=?1 and t.status='0'")
    List<TopicSelect> findByTeacher(String account);

    @Modifying
    @Query("update TopicSelect t set t.status='1' where t.id=?1")
    void updateStatusById(Integer id);

    @Modifying
    @Query("update TopicSelect t set t.status='2' where t.id=?1")
    void updateStatusById1(Integer id);

    Optional<TopicSelect> findById(Integer id);
}
