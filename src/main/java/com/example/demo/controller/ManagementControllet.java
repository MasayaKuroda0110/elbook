package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 管理機能一覧画面コントローラー
 */
@Controller
public class ManagementControllet {

	@GetMapping("/management")
	public String management(Model model) {
		return "management";
	}

}