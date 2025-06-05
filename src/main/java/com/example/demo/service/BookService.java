package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
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
	
	/**
	 * ログイン中のユーザーが借りている書籍リスト検索
	 * @param user ログイン中のユーザー
	 * @return レンタルリスト
	 */
	public List<Book> findAllRentalBooks(User user){
		List<Transaction> transactionLists = new ArrayList<>();
		List<Book> rentalLists = new ArrayList<>();
		transactionLists = transactionService.findAllTransaction();
		for(Transaction transaction:transactionLists) {
			if(transaction.getTransacitonType().equals("貸出") && transaction.getUser()==user) {
				rentalLists.add(transaction.getBook());
			}
		}
		
		return rentalLists;
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