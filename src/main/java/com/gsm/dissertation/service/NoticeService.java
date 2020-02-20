package com.gsm.dissertation.service;

import com.gsm.dissertation.model.Notice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface NoticeService {
    void save(Notice notice);

    List<Notice> findByGetUserAccount(String getUserAccount);

    @Transactional
    void updateById(Integer id);

    List<Notice> findAllByGetUserAccount(String getUserAccount);

    Notice findById(Integer id);
}
