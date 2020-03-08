package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.TopicUploadRepository;
import com.gsm.dissertation.model.TopicUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicUploadServiceImpl implements TopicUploadService {
    @Autowired
    private TopicUploadRepository topicUploadRepository;

    @Override
    public void save(TopicUpload topicUpload) {
        topicUploadRepository.save(topicUpload);
    }

    @Override
    public Integer getUploadCountByStudentAccount(String account) {
        return topicUploadRepository.getUploadCountByStudentAccount(account);
    }

    @Override
    public List<TopicUpload> findByTeacher(String account) {
        return topicUploadRepository.findByTeacher(account);
    }

    @Override
    public Optional<TopicUpload> findById(Integer id) {
        return topicUploadRepository.findById(id);
    }
}
