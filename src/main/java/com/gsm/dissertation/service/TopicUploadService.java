package com.gsm.dissertation.service;


import com.gsm.dissertation.model.TopicUpload;

public interface TopicUploadService {
    void save(TopicUpload topicUpload);

    Integer getUploadCountByStudentAccount(String account);
}
