package com.wey.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login.html")
    public String login() {
        return "login";
    }
    
    @GetMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
    
    @GetMapping("/logout-success")
    public String logout() {
        return "login";
    }
    
}
