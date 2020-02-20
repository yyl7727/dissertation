package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice,Integer> {
    @Query("select t from Notice t where t.getUserAccount=?1 and t.status='0'")
    List<Notice> findByGetUserAccount(String getUserAccount);

    @Modifying
    @Query("update Notice t set t.status='1' where t.id=?1")
    void updateById(Integer id);

    @Query("select t from Notice t where t.getUserAccount=?1 order by t.sendTime desc")
    List<Notice> findAllByGetUserAccount(String getUserAccount);

    Optional<Notice> findById(Integer id);
}
