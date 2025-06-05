package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;





@Controller
public class BookListController{
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/bookList")
	public String getBooks(Model model) {
		List<Book> books = bookService.findAllBooks();
		model.addAttribute("books",books);
		return "bookList";
	}
}