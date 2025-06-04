package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;



@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer>{
	boolean existsByBookAndReturnDateIsNull(Book book);
	Transaction findByBook(Book book);
}