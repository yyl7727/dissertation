package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.TopicRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicReleaseRepository extends JpaRepository<TopicRelease,Integer> {
    @Query("select t from TopicRelease t where t.topicMajor=?1")
    List<TopicRelease> findTopicReleaseByMajor(String major);

    @Modifying
    @Query("update TopicRelease t set t.studentCount=t.studentCount+1 where t.id=?1")
    void updateCountById(Integer id);
}
