package com.wey.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wey.springboot.pojo.Person;

@Controller
@RequestMapping("/index")
public class IndexController {
    
    @GetMapping
    public String index(Model model) {
        Person single = new Person();
        single.setName("Jack");
        single.setAge(25);
        
        // ThymeleafProperties
        // WebMvcProperties
        // WebMvcAutoConfiguration
        // ServerProperties
        
        List<Person> people = new ArrayList<Person>();
        Person p1 = new Person("Ken", 26);
        Person p2 = new Person("Sam", 26);
        Person p3 = new Person("Chris", 26);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        model.addAttribute("singlePerson", single);
        model.addAttribute("people", people);
        return "index";
    }
}
