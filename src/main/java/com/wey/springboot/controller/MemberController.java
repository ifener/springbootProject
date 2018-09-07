package com.wey.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class MemberController {
    
    @GetMapping("/index")
    public String index() {
        return "users/index";
    }
}
