package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.TopicReleaseRepository;
import com.gsm.dissertation.model.TopicRelease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicReleaseImpl implements TopicReleaseService {
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
}
