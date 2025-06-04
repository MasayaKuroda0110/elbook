package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.service.BookService;
import com.example.demo.service.TransactionService;



@Controller
public class EditBookController{
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/editBook")
	public String getBook(@RequestParam("id") Integer id, Model model) {
		Book book = bookService.findBook(id);
		Transaction transaction = transactionService.findTransaction(book);
		model.addAttribute("book",book);
		model.addAttribute("transaction",transaction);
		return "editBook";
	}
	
	@PostMapping("/Book")
	public String saveBook(@ModelAttribute("book") Book book, Model model) {
		bookService.saveBook(book);
		return "redirect:/bookList";
	}
}