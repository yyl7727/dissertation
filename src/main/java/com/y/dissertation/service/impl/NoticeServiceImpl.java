package com.y.dissertation.service.impl;

import com.y.dissertation.dao.NoticeRepository;
import com.y.dissertation.model.Notice;
import com.y.dissertation.service.NoticeService;
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

    @Override
    public Notice findById(Integer id) {
        if (noticeRepository.findById(id).isPresent()){
            return noticeRepository.findById(id).get();
        }else {
            return new Notice();
        }
    }
}
