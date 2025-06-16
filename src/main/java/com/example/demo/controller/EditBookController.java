package com.example.demo.controller;

import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;

/**
 * 書籍編集画面コントローラー
 */
@Controller
public class EditBookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	@GetMapping("/editBook")
	public String getBook(@RequestParam("id") Integer id,@RequestParam(required = false) String type, RedirectAttributes redirectAttributes, Model model) {
		Book book = null;
		
		try {
			book = bookService.findBook(id);
		} catch (NoSuchElementException ex) {
			redirectAttributes.addFlashAttribute("message","書籍が見つかりませんでした");
			if(type.equals("home")) {
				return "redirect:/home";
			}
			return "redirect:/bookList";
		}
		
		Transaction transaction = transactionService.findTransaction(book);
		User currentUser = getCurrentUser();
		model.addAttribute("book", book);
		model.addAttribute("transaction", transaction);
		model.addAttribute("currentUser", currentUser);
		return "editBook";
	}

	@PostMapping("/Book")
	public String handleBookForm(@ModelAttribute("book") Book book,
			@RequestParam String action,
			@RequestParam(required = false) Integer transactionId,
			Model model,
			RedirectAttributes redirectAttributes) {
		if ("update".equals(action)) {
			bookService.saveBook(book);
		} else if ("rental".equals(action)) {
			transactionService.borrowBook(getCurrentUser(), book, new Date());
		} else if ("return".equals(action)) {
			transactionService.returnBook(book, new Date());
		} else if ("delete".equals(action)) {
			bookService.deleteBook(book);
		}

		return "redirect:/bookList";
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