package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.TopicRelease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicReleaseRepository extends JpaRepository<TopicRelease,Integer> {
}
