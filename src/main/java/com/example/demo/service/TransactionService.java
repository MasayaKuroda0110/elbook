package com.example.demo.service;

import java.util.Date;
import java.util.List;

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
		transaction.setTransactionType("本棚");
		transaction.setBook(book);
		transactionRepository.save(transaction);
	}
	
	public Transaction borrowBook(User user,Book book,Date borrowDate) {
		Transaction existingTransaction = transactionRepository.findByBook(book);
		
		existingTransaction.setUser(user);
		existingTransaction.setTransactionType("貸出");
		existingTransaction.setBorrowDate(new Date());
		transactionRepository.save(existingTransaction);
		return existingTransaction;
	}
	
	public Transaction returnBook(Book book,Date returnDate) {
		Transaction existingTransaction = transactionRepository.findByBook(book);
		
		existingTransaction.setTransactionType("本棚");
		existingTransaction.setReturnDate(returnDate);
		transactionRepository.save(existingTransaction);
		return existingTransaction;
	}
	
    public boolean isBookCurrentlyBorrowed(Book book) {
        return transactionRepository.existsByBookAndReturnDateIsNull(book);
    }
    
    public Transaction findTransaction(Book book) {
    	return transactionRepository.findByBook(book);
    }
    
    public List<Transaction> findAllTransaction(){
    	return transactionRepository.findAll();
    }
}