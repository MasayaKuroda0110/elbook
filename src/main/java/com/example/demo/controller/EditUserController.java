package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * 管理画面（設定画面）コントローラー
 */
@Controller
public class EditUserController {

	@Autowired
	private UserService userService;

	@GetMapping("/adminSetting")
	public String adminSetting(@RequestParam("id") Integer id, Model model) {
		User user = userService.findByUserId(id);
		model.addAttribute("user", user);
		model.addAttribute("roles", List.of("ROLE_ADMIN", "ROLE_USER"));
		return "adminSetting";
	}

	@PostMapping("/adminSetting")
	public String updateUser(@ModelAttribute("user") User user,
			@RequestParam String action) {

		if ("update".equals(action)) {
			userService.updateUser(user);
			Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails updatedUser = userService.loadUserByUsername(user.getUserName());
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
					updatedUser,
					currentAuth.getCredentials(),
					updatedUser.getAuthorities()));
			if(user.getRole().equals("ROLE_USER")) {
				return "home";
			}
		} else if ("delete".equals(action)) {
			userService.deleteUser(user);
		}
		return "redirect:/userManagement";
	}

}