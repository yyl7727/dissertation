package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.TopicReleaseRepository;
import com.gsm.dissertation.model.TopicRelease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicReleaseImpl implements TopicReleaseService {
    @Autowired
    TopicReleaseRepository topicReleaseRepository;

    @Override
    public List<TopicRelease> findByMajor(String major) {
        return topicReleaseRepository.findTopicReleaseByMajor(major);
    }
}
