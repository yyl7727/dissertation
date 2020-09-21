package com.y.dissertation.dao;

import com.y.dissertation.model.Defence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DefenceRepository extends JpaRepository<Defence,Integer> {

    List<Defence> findAllByTeacherAccount(String account);

    @Modifying
    @Query("update Defence t set t.result=?2 where t.studentAccount=?1")
    void updateResult(String account, String result);

    Optional<Defence> getById(Integer id);

    @Query("select t from Defence t")
    List<Defence> findAll();
}
