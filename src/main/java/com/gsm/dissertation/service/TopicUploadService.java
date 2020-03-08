package com.gsm.dissertation.service;


import com.gsm.dissertation.model.TopicUpload;

import java.util.List;
import java.util.Optional;

public interface TopicUploadService {
    void save(TopicUpload topicUpload);

    Integer getUploadCountByStudentAccount(String account);

    List<TopicUpload> findByTeacher(String account);

    Optional<TopicUpload> findById(Integer id);
}
