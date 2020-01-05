package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentTypeRepository extends JpaRepository<ContentType, Integer> {

    List<ContentType> findByParent(ContentType parent);

}
