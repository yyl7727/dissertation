package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.TopicRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicReleaseRepository extends JpaRepository<TopicRelease,Integer> {
    @Query("select t from TopicRelease t where t.topicMajor=?1")
    List<TopicRelease> findTopicReleaseByMajor(String major);
}
