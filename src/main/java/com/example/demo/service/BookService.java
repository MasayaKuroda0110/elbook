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

	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	/**
	 * ログイン中のユーザーが借りている書籍リスト検索
	 * @param user ログイン中のユーザー
	 * @return レンタルリスト
	 */
	public List<Book> findAllRentalBooks(User user) {
		List<Transaction> transactionLists = new ArrayList<>();
		List<Book> rentalLists = new ArrayList<>();
		transactionLists = transactionService.findAllTransaction();
		for (Transaction transaction : transactionLists) {
			if (transaction.getTransactionType().equals("貸出") && transaction.getUser() == user) {
				rentalLists.add(transaction.getBook());
			}
		}

		return rentalLists;
	}

	/**
	 * 書籍テーブルより、IDに紐づいた書籍検索
	 * @param Id 書籍ID
	 * @return 書籍データ
	 */
	public Book findBook(Integer Id) {
		return bookRepository.findByBookId(Id).getFirst();
	}
	
    public List<Book> searchByTitle(String query) {
        return bookRepository.findByTitleContaining(query);
    }

    public List<Book> searchByAuthor(String query) {
        return bookRepository.findByAuthorNameContaining(query);
    }

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

}