package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

@Controller
public class BookListController{
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/bookList")
	public String getBooks(Model model) {
	    model.addAttribute("books", bookService.findAllBooks());
	    return "bookList";
	}
	
	@GetMapping("/books")
	@ResponseBody
	public List<Book> searchBooks(
	    @RequestParam(value = "query", required = false) String query,
	    @RequestParam(value = "searchBy", required = false) List<String> searchBy) {

	    // クエリが空の場合、すべてのデータを返す
	    if (query == null || query.isEmpty()) {
	        return bookService.findAllBooks(); // 全データ取得
	    }

	    // 条件がない場合は空リストを返す
	    if (searchBy == null || searchBy.isEmpty()) {
	        return Collections.emptyList();
	    }

	    // 条件に応じた検索
	    if (searchBy.contains("title") && searchBy.contains("author")) {
	        return bookService.searchByTitleOrAuthor(query);
	    } else if (searchBy.contains("title")) {
	        return bookService.searchByTitle(query);
	    } else if (searchBy.contains("author")) {
	        return bookService.searchByAuthor(query);
	    }
	    return Collections.emptyList();
	}
}