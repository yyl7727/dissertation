package com.y.dissertation.dao;

import com.y.dissertation.model.TopicRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicReleaseRepository extends JpaRepository<TopicRelease,Integer> {
    @Query("select t from TopicRelease t where t.topicMajor=?1 and t.t_status='0'")
    List<TopicRelease> findTopicReleaseByMajor(String major);

    @Modifying
    @Query("update TopicRelease t set t.studentCount=t.studentCount+1 where t.id=?1")
    void updateCountById(Integer id);

    @Query("select t from TopicRelease t where t.teacherAccount=?1")
    List<TopicRelease> findAllByTeacherAccount(String teachAccount);

    @Modifying
    @Query("update TopicRelease t set t.t_status=1 where t.tid=?1")
    void updateTopicStatus(Integer id);
}
