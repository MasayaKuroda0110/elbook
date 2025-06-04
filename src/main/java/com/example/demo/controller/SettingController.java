package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class SettingController{
	
	@Autowired
	private UserService userService;
	
    @GetMapping("/setting")
    public String showSetting(Model model, Principal principal) {
        String username = principal.getName(); 
        User user = userService.findByUsername(username); 
        model.addAttribute("user", user);
        return "setting";
    }
    
    @PostMapping("/setting")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "home";
    }
}