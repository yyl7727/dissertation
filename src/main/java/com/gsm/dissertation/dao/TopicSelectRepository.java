package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.TopicSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicSelectRepository extends JpaRepository<TopicSelect, Integer> {
    @Query("select count(t) from TopicSelect t where t.sId=?1")
    Integer findCountByStudent(String sId);
}
