package com.wey.springboot.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class CustomRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable>
        extends JpaRepositoryFactoryBean<T, S, ID> {
    
    public CustomRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }
    
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new CustomRepositoryFactory(entityManager);
    }
    
    private static class CustomRepositoryFactory<S, ID extends Serializable> extends JpaRepositoryFactory {
        
        private final EntityManager em;
        
        public CustomRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.em = entityManager;
        }
        
        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            return new CustomRepositoryImpl<S, ID>((Class<S>) information.getDomainType(), em);
        }
        
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return CustomRepositoryImpl.class;
        }
        
    }
    
}
