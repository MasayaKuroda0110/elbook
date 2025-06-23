package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * 新規ユーザー登録画面コントローラー
 */
@Controller
public class RegisterController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String adminSetting(Model model) {
		model.addAttribute("user", new User()); 
		model.addAttribute("roles", List.of("ROLE_ADMIN", "ROLE_USER"));
		return "register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("user") User user,RedirectAttributes redirectAttributes) {
		try {
			userService.save(user);
		} catch (Exception e) {
			//登録の失敗
			redirectAttributes.addFlashAttribute("message","ユーザーの登録に失敗しました");
			return "redirect:/register";
		}
		return "redirect:/userManagement";
	}
}
