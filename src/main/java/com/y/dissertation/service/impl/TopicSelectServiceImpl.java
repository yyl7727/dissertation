package com.y.dissertation.service.impl;

import com.y.dissertation.dao.TopicSelectRepository;
import com.y.dissertation.model.TopicSelect;
import com.y.dissertation.service.TopicSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicSelectServiceImpl implements TopicSelectService {
    @Autowired
    TopicSelectRepository topicSelectRepository;

    @Override
    public void save(TopicSelect topicSelect) {
        topicSelectRepository.save(topicSelect);
    }

    @Override
    public Integer findCountByStudent(String account) {
        return topicSelectRepository.findCountByStudent(account);
    }

    @Override
    public List<TopicSelect> findByTeacher(String account) {
        return topicSelectRepository.findByTeacher(account);
    }

    @Transactional
    @Override
    public void updateStatusById(Integer id) {
        topicSelectRepository.updateStatusById(id);
    }

    @Transactional
    @Override
    public void updateStatusById1(Integer id) {
        topicSelectRepository.updateStatusById1(id);
    }

    @Override
    public TopicSelect findById(Integer id) {
        return topicSelectRepository.findById(id).get();
    }

    @Override
    public List<TopicSelect> findAll() {
        return topicSelectRepository.findAll();
    }
}
