package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SidebarController{
	
	@GetMapping("/sidebar/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/sidebar/setting")
	public String setting() {
		return "setting";
	}
	
	@GetMapping("/regBook")
	public String regBook() {
		return "regBook";
	}
	
	@GetMapping("/sidebar/bookList")
	public String bookList() {
		return "bookList";
	}
	
	@GetMapping("/userManagement")
	public String userManagement() {
		return "userManagement";
	}
	
}