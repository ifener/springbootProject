package com.wey.springboot.service;

import com.wey.springboot.pojo.Person;

public interface PersonService {
    
    Person savePersonWithRollback(Person person);
    
    Person savePersonWithoutRollback(Person person);
    
    Person savePerson();
    
    Person save(Person person);
    
    void remove(Long id);
    
    Person findOne(Person person);
    
}
