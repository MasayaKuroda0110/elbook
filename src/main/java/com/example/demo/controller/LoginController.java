package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;

/**
 * ログイン画面コントローラー
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String redirectToIndex() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 現在のユーザーの認証情報を取得します
		if (authentication != null && authentication.isAuthenticated()) {
			return "redirect:/home";
		}
		return "redirect:/login";
	}

	@GetMapping("/home")
	public String home(@ModelAttribute("message") String message,Model model) {
		List<Book> books = bookService.findAllRentalBooks(getCurrentUser());
		model.addAttribute("books", books);
		model.addAttribute("message",message);
		return "home";
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