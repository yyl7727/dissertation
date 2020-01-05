package com.gsm.dissertation.service;

import com.gsm.dissertation.dao.ContentTypeRepository;
import com.gsm.dissertation.model.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class ContentTypeServiceImpl implements ContentTypeService {
    @Autowired
    private ContentTypeRepository contentTypeRepository;

    @Override
    public void save(ContentType contentType) {
        contentTypeRepository.save(contentType);
    }

    @Override
    public void delete(ContentType contentType) {
        contentTypeRepository.delete(contentType);
    }

    @Override
    @Transactional
    public void deletes(Iterable<ContentType> contentTypes) {
        contentTypeRepository.deleteAll(contentTypes);
    }

    @Override
    public void deleteById(Integer id) {
        contentTypeRepository.deleteById(id);
    }

    @Override
    public List<ContentType> findByParent(ContentType contentTypeParent) {
        return contentTypeRepository.findByParent(contentTypeParent);
    }

    @Override
    public List<ContentType> findAll() {
        return contentTypeRepository.findAll();
    }
}
