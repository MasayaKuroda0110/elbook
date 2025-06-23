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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;

/**
 * 管理画面（設定画面）コントローラー
 */
@Controller
public class EditUserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;

	@GetMapping("/adminSetting")
	public String adminSetting(@RequestParam("id") Integer id,RedirectAttributes redirectAttributes, Model model) {
		User user = userService.findByUserId(id);
		if(user == null) {
			redirectAttributes.addFlashAttribute("message","ユーザーが見つかりませんでした");
			return "redirect:/userManagement";
		}
		model.addAttribute("user", user);
		model.addAttribute("roles", List.of("ROLE_ADMIN", "ROLE_USER"));
		return "adminSetting";
	}

	@PostMapping("/adminSetting")
	public String updateUser(@ModelAttribute("user") User user,
			@RequestParam String action,
			RedirectAttributes redirectAttributes) {

		if ("update".equals(action)) {
			
			try {
				userService.updateUser(user);
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message","ユーザーの更新に失敗しました");
				return "redirect:/userManagement";
			}
			
			
			User currentUser = getCurrentUser();
			if(user.getRole().equals("ROLE_USER")) {
			    // 認証情報を更新
			    Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
			    UserDetails updatedUser = userService.loadUserByUsername(user.getUserName());
			    Authentication newAuth = new UsernamePasswordAuthenticationToken(
			        updatedUser, 
			        currentAuth.getCredentials(), 
			        updatedUser.getAuthorities()
			    );
			    // ログインユーザーと更新ユーザーの一致を確認
			    if(currentUser.getUserId() == userService.findByUsername(updatedUser.getUsername()).getUserId()) {
				    SecurityContextHolder.getContext().setAuthentication(newAuth);
					return "home";
			    }

			}
		} else if ("delete".equals(action)) {
			if(transactionService.findTransactionByUserIdTransactionType(user).size() == 0) {
				userService.deleteUser(user);
			} else {
				redirectAttributes.addFlashAttribute("message","このユーザーは現在書籍貸出中なので削除できません");
				return "redirect:/userManagement";
			}
		}
		return "redirect:/userManagement";
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