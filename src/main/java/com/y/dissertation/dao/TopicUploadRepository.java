package com.y.dissertation.dao;

import com.y.dissertation.model.TopicUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicUploadRepository extends JpaRepository<TopicUpload,Integer> {
    @Query("select count(t) from TopicUpload t where t.studentAccount=?1")
    Integer getUploadCountByStudentAccount(String account);

    @Query("select t from TopicUpload t where t.studentAccount=?1 order by t.uploadTime desc")
    List<TopicUpload> findByTeacher(String account);
}
