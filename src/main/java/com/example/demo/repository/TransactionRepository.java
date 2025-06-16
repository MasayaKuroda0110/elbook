package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;


/**
 * 貸出管理リポジトリ
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	boolean existsByBookAndReturnDateIsNull(Book book);

	Transaction findByBook(Book book);
	
	List<Transaction> findByUser(User user);
}