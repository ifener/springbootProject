package com.wey.springboot.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/webSocketCenter")
public class WebSocketController {
    
    // 页面请求
    @GetMapping("/socket/{username}")
    public ModelAndView socket(@PathVariable String username) {
        ModelAndView mav = new ModelAndView("ws");
        mav.addObject("username", username);
        return mav;
    }
    
    // 推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{username}")
    public String pushToWeb(@PathVariable String username, String message) {
        try {
            WebSocketServer.sendInfo(message, username);
        }
        catch (IOException e) {
            e.printStackTrace();
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }
}
