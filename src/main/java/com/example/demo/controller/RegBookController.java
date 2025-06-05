package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.BookDto;
import com.example.demo.service.BookService;

@Controller
public class RegBookController{
	
	@Autowired
	private BookService BookService;
	
	@PostMapping("/regBook")
	public String registerBook(@ModelAttribute BookDto bookDto) {
		BookService.registerBook(bookDto.getTitle(), bookDto.getName(), bookDto.getSummary());
		return "redirect:/home";
	}
}