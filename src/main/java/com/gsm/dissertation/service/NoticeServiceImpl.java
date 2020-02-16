package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.NoticeRepository;
import com.gsm.dissertation.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public List<Notice> findByGetUserAccount(String getUserAccount) {
        return noticeRepository.findByGetUserAccount(getUserAccount);
    }

    @Override
    public void updateById(Integer id) {
        noticeRepository.updateById(id);
    }

    @Override
    public void save(Notice notice) {
        noticeRepository.save(notice);
    }

    @Override
    public List<Notice> findAllByGetUserAccount(String getUserAccount) {
        return noticeRepository.findAllByGetUserAccount(getUserAccount);
    }
}
