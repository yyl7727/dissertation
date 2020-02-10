package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.TopicSelectRepository;
import com.gsm.dissertation.model.TopicSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicSelectImpl implements TopicSelectService {
    @Autowired
    TopicSelectRepository topicSelectRepository;

    @Override
    public Integer findCountByStudent(String sId) {
        return topicSelectRepository.findCountByStudent(sId);
    }

    @Override
    public List<TopicSelect> findByTeacher(String tid) {
        return topicSelectRepository.findByTeacher(tid);
    }
}
