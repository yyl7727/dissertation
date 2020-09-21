package com.y.dissertation.service;


import com.y.dissertation.model.TopicRelease;

import java.util.List;

public interface TopicReleaseService {
    List<TopicRelease> findByMajor(String major);

    String save(TopicRelease topicRelease);

    String updateCountById(Integer id);

    List<TopicRelease> findAllByTeacherAccount(String teachAccount);

    String updateTopicStatus(Integer id);

    List<TopicRelease> findAll();
}
