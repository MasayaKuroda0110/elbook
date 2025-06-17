package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

import jakarta.transaction.Transactional;

/**
 * 書籍サービス
 */
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private TransactionService transactionService;

	/**
	 * 書籍テーブルに、書籍を新規保存
	 * @param title タイトル
	 * @param name 著者名
	 * @param summary あらすじ・概要
	 */
	public void registerBook(String title, String name, String summary) {
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

	/**
	 * 書籍一覧の検索
	 * @return 書籍一覧
	 */
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	/**
	 * ログイン中のユーザーが借りている書籍リスト検索
	 * @param user ログイン中のユーザー
	 * @return レンタルリスト
	 */
	public List<Book> findAllRentalBooks(User user) {
		List<Book> rentalLists = new ArrayList<>();
		List<Transaction> transactionLists = transactionService.findByUserAndTransactionType(user, "貸出");
		for (Transaction transaction : transactionLists) {
			rentalLists.add(transaction.getBook());
		}

		return rentalLists;
	}

	/**
	 * 書籍テーブルより、IDに紐づいた書籍検索
	 * @param Id 書籍ID
	 * @return 書籍データ
	 */
	public Book findBook(Integer Id) throws NoSuchElementException{
	    List<Book> books = bookRepository.findByBookId(Id);
	    if (books.isEmpty()) {
	        throw new NoSuchElementException("No book found for the given ID: " + Id);
	    }
	    return books.get(0);
	}

	/**
	 * タイトルで書籍検索
	 * @param query タイトル
	 * @return 書籍データ
	 */
	public List<Book> searchByTitle(String query) {
		return bookRepository.findByTitleContaining(query);
	}

	/**
	 * 著者で書籍検索
	 * @param query 著者名
	 * @return 書籍データ
	 */
	public List<Book> searchByAuthor(String query) {
		return bookRepository.findByAuthorNameContaining(query);
	}

	/**
	 * タイトルと著者で書籍検索
	 * @param query 著者名orタイトル
	 * @return 書籍データ
	 */
	public List<Book> searchByTitleOrAuthor(String query) {
		return bookRepository.findByTitleContainingOrAuthorNameContaining(query, query);
	}

	/**
	 * 書籍テーブルの、書籍を更新
	 * @param book 書籍データ
	 */
	public void saveBook(Book book) {
		Author author = authorRepository.findByName(book.getAuthor().getName())
				.orElseGet(() -> {
					Author newAuthor = new Author();
					newAuthor.setName(book.getAuthor().getName());
					return authorRepository.save(newAuthor);
				});
		Transaction transaction = transactionService.findTransaction(book);
		book.setTransaction(transaction);
		book.setAuthor(author);
		bookRepository.save(book);
	}

	/**
	 * 書籍の削除
	 * @param book 書籍データ
	 */
	@Transactional
	public void deleteBook(Book book) {
		transactionService.deleteTransaction(book);
		bookRepository.delete(book);
	}

}