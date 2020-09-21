package com.y.dissertation.service.impl;

import com.y.dissertation.dao.TopicReleaseRepository;
import com.y.dissertation.model.TopicRelease;
import com.y.dissertation.service.TopicReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicReleaseServiceImpl implements TopicReleaseService {
    @Autowired
    TopicReleaseRepository topicReleaseRepository;

    @Override
    public List<TopicRelease> findByMajor(String major) {
        return topicReleaseRepository.findTopicReleaseByMajor(major);
    }

    @Override
    public String save(TopicRelease topicRelease) {
        try {
            topicReleaseRepository.save(topicRelease);
            return "0";
        }catch (Exception ex){
            return "保存失败,错误信息："+ex.getMessage();
        }
    }

    /**
     * 更新选课的学生人数+1
     * @param id 课题id
     * @return 结果
     */
    @Transactional
    @Override
    public String updateCountById(Integer id) {
        try {
            topicReleaseRepository.updateCountById(id);
            return "0";
        }catch (Exception ex){
            return "更新失败,错误信息:"+ex.getMessage();
        }
    }

    @Override
    public List<TopicRelease> findAllByTeacherAccount(String teachAccount) {
        List<TopicRelease> topicReleaseList = null;
        try {
            topicReleaseList = topicReleaseRepository.findAllByTeacherAccount(teachAccount);
            return topicReleaseRepository.findAllByTeacherAccount(teachAccount);
        }catch (Exception ex){
            return topicReleaseList;
        }
    }

    /**
     * 关闭课题
     * @param id 课题id
     * @return 关闭结果
     */
    @Transactional
    @Override
    public String updateTopicStatus(Integer id) {
        try {
            topicReleaseRepository.updateTopicStatus(id);
            return "0";
        }catch (Exception ex){
            return "课题关闭失败,错误信息:" + ex.getMessage();
        }
    }

    @Override
    public List<TopicRelease> findAll() {
        return topicReleaseRepository.findAll();
    }
}
