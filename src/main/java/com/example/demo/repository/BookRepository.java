package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Book;

/**
 * 書籍リポジトリ
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
	List<Book> findByBookId(Integer bookId);

	List<Book> findByTitleContaining(String title);

	List<Book> findByAuthorNameContaining(String authorName);

	List<Book> findByTitleContainingOrAuthorNameContaining(String title, String authorName);
}