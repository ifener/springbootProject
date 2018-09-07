package com.wey.springboot.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wey.springboot.dao.PersonRepository;
import com.wey.springboot.pojo.Person;
import com.wey.springboot.service.Person2Service;
import com.wey.springboot.service.PersonService;

@Service("personServiceImpl")
public class PersonServiceImpl implements PersonService {
    
    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    @Qualifier("personServiceImpl2")
    Person2Service personService;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Person savePersonWithRollback(Person person) {
        Person p = personRepository.save(person);
        if (p.getName().equals("Ken")) {
            throw new IllegalArgumentException("Ken");
        }
        
        try {
            personService.savePerson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
    
    @Transactional(noRollbackFor = { IllegalArgumentException.class })
    public Person savePersonWithoutRollback(Person person) {
        Person p = personRepository.save(person);
        if (p.getName().equals("Ken")) {
            throw new IllegalArgumentException("Ken");
        }
        return p;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Person savePerson() {
        Person person = new Person();
        person.setName("REQUIRES_NEW");
        person.setAge(10);
        personRepository.save(person);
        if (person.getAge() == 10) {
            throw new IllegalArgumentException("Illegal Age!!");
        }
        return person;
    }
    
    @CachePut(value = "demoCache", key = "#person.id")
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("Put id is " + p.getId() + " Person into cache");
        return p;
    }
    
    @CacheEvict(value = "demoCache")
    public void remove(Long id) {
        personRepository.deleteById(id);
        System.out.println("Remove id is " + id);
    }
    
    @Cacheable(value = "demoCache", key = "#person.id")
    public Person findOne(Person person) {
        Optional<Person> p = personRepository.findById(person.getId());
        if (p.isPresent()) {
            System.out.println("Put id is " + p.get().getId() + " Person into cache");
            return p.get();
        }
        return null;
    }
    
}
