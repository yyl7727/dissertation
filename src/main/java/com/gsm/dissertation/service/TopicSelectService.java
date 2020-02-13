package com.gsm.dissertation.service;

import com.gsm.dissertation.model.TopicSelect;

import java.util.List;


public interface TopicSelectService {
    void save(TopicSelect topicSelect);

    Integer findCountByStudent(String account);

    List<TopicSelect> findByTeacher(String account);

    void updateStatusById(Integer id);

    void updateStatusById1(Integer id);

    TopicSelect findById(Integer id);
}
