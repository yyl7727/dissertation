package com.gsm.dissertation.service;


import com.gsm.dissertation.model.TopicUpload;

import java.util.List;

public interface TopicUploadService {
    void save(TopicUpload topicUpload);

    Integer getUploadCountByStudentAccount(String account);

    List<TopicUpload> findByTeacher(String account);
}
