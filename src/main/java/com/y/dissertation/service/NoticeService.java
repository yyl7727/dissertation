package com.y.dissertation.service;

import com.y.dissertation.model.Notice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoticeService {
    void save(Notice notice);

    List<Notice> findByGetUserAccount(String getUserAccount);

    @Transactional
    void updateById(Integer id);

    List<Notice> findAllByGetUserAccount(String getUserAccount);

    Notice findById(Integer id);
}
