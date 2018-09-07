package com.wey.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wey.springboot.dao.PersonRepository;
import com.wey.springboot.pojo.Person;
import com.wey.springboot.service.Person2Service;

@Service("personServiceImpl2")
public class PersonServiceImpl2 implements Person2Service {
    
    @Autowired
    PersonRepository personRepository;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Person savePerson() {
        // try {
        Person person = new Person();
        person.setName("REQUIRES_NEW");
        person.setAge(10);
        personRepository.save(person);
        if (person.getAge() == 10) {
            throw new IllegalArgumentException("Illegal Age!!");
        }
        
        return person;
        // }
        // catch (Exception ex) {
        // return null;
        // }
    }
    
}
