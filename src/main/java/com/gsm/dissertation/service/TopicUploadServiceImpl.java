package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.TopicUploadRepository;
import com.gsm.dissertation.model.TopicUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
