package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

@Service
public class BookService{
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	public void registerBook(String title,String name,String summary) {
		Author author = authorRepository.findByName(name)
				.orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(name);
                    return authorRepository.save(newAuthor);
                });
		
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setSummary(summary);
		bookRepository.save(book);
		transactionService.regBook(book);
	}
	
	public List<Book> findAllBooks(){
		return bookRepository.findAll();
	}
	
	public Book findBook(Integer Id) {
		return bookRepository.findByBookId(Id).getFirst();
	}
	
	public void saveBook(Book book) {
		Author author = authorRepository.findByName(book.getAuthor().getName())
				.orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(book.getAuthor().getName());
                    return authorRepository.save(newAuthor);
                });
		book.setAuthor(author);
		bookRepository.save(book);
	}
	
}