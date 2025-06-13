package com.example.demo.controller;

import java.security.Principal;

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

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * 設定画面コントローラー
 */
@Controller
public class SettingController {

	@Autowired
	private UserService userService;

	@GetMapping("/setting")
	public String showSetting(Model model, Principal principal) {
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			return "redirect:/login"; // 認証されていない場合のリダイレクト
		}
		model.addAttribute("user", currentUser);
		return "setting";
	}

	@PostMapping("/setting")
	public String updateUser(@ModelAttribute("user") User user) {
		userService.updateUser(user);

		// 認証情報を更新
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails updatedUser = userService.loadUserByUsername(user.getUserName());
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
				updatedUser,
				currentAuth.getCredentials(),
				updatedUser.getAuthorities()));

		return "redirect:/home";
	}

	/**
	 * 現在のログイン中のユーザー情報取得
	 * @return ログイン中のユーザー情報
	 */
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) principal;
				return userService.findByUsername(userDetails.getUsername());
			}
		}
		return null;
	}
}