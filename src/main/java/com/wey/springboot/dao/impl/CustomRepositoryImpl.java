package com.wey.springboot.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.wey.springboot.dao.CustomRepository;
import com.wey.springboot.specs.CustomerSpecs;

public class CustomRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements CustomRepository<T, ID> {
    
    private final EntityManager entityManager;
    private final Class<T> entityClass;
    
    public CustomRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
        this.entityClass = domainClass;
    }
    
    public List<T> findByAuto(T example) {
        return findAll(CustomerSpecs.byAuto(entityManager, example));
    }
    
}
