package com.gsm.dissertation.service;

import com.gsm.dissertation.model.ContentType;

import java.util.List;

public interface ContentTypeService {
    void save(ContentType contentType);

    void delete(ContentType contentType);

    void deletes(Iterable<ContentType> contentTypes);

    void deleteById(Integer id);

    List<ContentType> findByParent(ContentType contentTypeParent);

    List<ContentType> findAll();
}
