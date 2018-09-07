package com.wey.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wey.springboot.dao.PersonRepository;
import com.wey.springboot.pojo.Person;
import com.wey.springboot.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
    
    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    @Qualifier("personServiceImpl")
    PersonService personService;
    
    @RequestMapping("/save")
    public Person save(String name, String address, Integer age) {
        Person p = personRepository.save(new Person(null, name, age, address));
        return p;
    }
    
    @RequestMapping("/q1")
    public List<Person> q1(String address) {
        return personRepository.findByAddress(address);
    }
    
    @RequestMapping("/q2")
    public Person q2(String name, String address) {
        return personRepository.findByNameAndAddress(name, address);
    }
    
    @RequestMapping("/q3")
    public Person q3(String name, String address) {
        return personRepository.withNameAndAddressQuery(name, address);
    }
    
    @RequestMapping("/q4")
    public Person q4(String name, String address) {
        return personRepository.withNameAndAddressNamedQuery(name, address);
    }
    
    @RequestMapping("/sort")
    public List<Person> sort() {
        return personRepository.findAll(new Sort(Direction.ASC, "age"));
    }
    
    @RequestMapping("/page")
    public Page<Person> page() {
        
        return personRepository.findAll(new QPageRequest(0, 2));
    }
    
    /* @RequestMapping("/auto")
    public List<Person> auto(Person person) {
        return personRepository.findByAuto(person);
    }*/
    
    @GetMapping("/rollback")
    public Person rollback(Person person) {
        return personService.savePersonWithRollback(person);
    }
    
    @GetMapping("/noRollback")
    public Person noRollback(Person person) {
        return personService.savePersonWithoutRollback(person);
    }
    
    @GetMapping("/put")
    public Person put(Person person) {
        return personService.save(person);
    }
    
    @GetMapping("/able")
    public Person able(Person person) {
        System.out.println(System.getProperty("user.home"));
        return personService.findOne(person);
    }
    
    @GetMapping("/evict")
    public void evict(Person person) {
        personService.remove(person.getId());
    }
}
