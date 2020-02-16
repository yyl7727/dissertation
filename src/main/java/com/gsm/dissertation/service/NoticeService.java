package com.gsm.dissertation.service;

import com.gsm.dissertation.model.Notice;

import java.util.List;

public interface NoticeService {
    void save(Notice notice);

    List<Notice> findByGetUserAccount(String getUserAccount);

    void updateById(Integer id);

    List<Notice> findAllByGetUserAccount(String getUserAccount);
}
