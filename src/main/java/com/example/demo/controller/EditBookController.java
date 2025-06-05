package com.example.demo.controller;

import java.util.Date;

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

import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;



@Controller
public class EditBookController{
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/editBook")
	public String getBook(@RequestParam("id") Integer id, Model model) {
		Book book = bookService.findBook(id);
		Transaction transaction = transactionService.findTransaction(book);
		Integer currentUserId = getCurrentUser().getUserId();
		model.addAttribute("book",book);
		model.addAttribute("transaction",transaction);
		model.addAttribute("currentUserId",currentUserId);
		return "editBook";
	}
	
	@PostMapping("/Book")
	public String handleBookForm(@ModelAttribute("book") Book book, 
						   @RequestParam String action,
						   @RequestParam (required = false) Integer transactionId,
						   Model model) {
		if("update".equals(action)) {
			bookService.saveBook(book);
		}else if("rental".equals(action)){	
			transactionService.borrowBook(getCurrentUser(), book, new Date());
		}else if("return".equals(action)) {
			transactionService.returnBook(book, new Date());
		}
		
		return "redirect:/bookList";
	}
	
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