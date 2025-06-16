package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * 管理画面（ユーザー一覧）コントローラー
 */
@Controller
public class UserManagementController {

	@Autowired
	private UserService userService;

	@GetMapping("/userManagement")
	public String getUserManagement(@ModelAttribute("message") String message,Model model) {
		List<User> users = userService.findAll();
		model.addAttribute("message", message);
		model.addAttribute("users", users);
		return "userManagement";
	}

}