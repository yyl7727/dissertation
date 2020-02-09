package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.TopicSelectRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicSelectImpl implements TopicSelectService {
    @Autowired
    TopicSelectRepository topicSelectRepository;

    @Override
    public Integer findCountByStudent(String sId) {
        return topicSelectRepository.findCountByStudent(sId);
    }
}
