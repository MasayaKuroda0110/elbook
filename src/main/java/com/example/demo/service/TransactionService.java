package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repository.TransactionRepository;

@Service
public class TransactionService{
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public void regBook(Book book) {
		Transaction transaction = new Transaction();
		transaction.setTransacitonType("本棚");
		transaction.setBook(book);
		transactionRepository.save(transaction);
	}
	
	public Transaction borrowBook(User user,Book book,Date borrowDate) {
		Transaction transaction = new Transaction();
		transaction.setUser(user);
		transaction.setBook(book);
		transaction.setTransacitonType("貸出");
		transaction.setBorrowDate(new Date());
		transactionRepository.save(transaction);
		return transaction;
	}
	
    public boolean isBookCurrentlyBorrowed(Book book) {
        return transactionRepository.existsByBookAndReturnDateIsNull(book);
    }
    
    public Transaction findTransaction(Book book) {
    	return transactionRepository.findByBook(book);
    }
}