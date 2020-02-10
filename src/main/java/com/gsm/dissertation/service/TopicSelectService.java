package com.gsm.dissertation.service;

import com.gsm.dissertation.model.TopicSelect;

import java.util.List;

public interface TopicSelectService {
    Integer findCountByStudent(String sId);

    List<TopicSelect> findByTeacher(String tid);
}
