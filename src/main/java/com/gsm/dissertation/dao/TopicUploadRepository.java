package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.TopicUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicUploadRepository extends JpaRepository<TopicUpload,Integer> {
    @Query("select count(t) from TopicUpload t where t.studentAccount=?1")
    Integer getUploadCountByStudentAccount(String account);
}
