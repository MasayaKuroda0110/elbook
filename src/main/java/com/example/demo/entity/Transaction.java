package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Transactions")
public class Transaction{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private int transactionID;
	
	@Column(name = "transaction_type")
	private String transacitonType;
	
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
	private Book book;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "borrow_date")
    private Date borrowDate;
    
    @Column(name = "return_date")
    private Date returnDate;

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public String getTransacitonType() {
		return transacitonType;
	}

	public void setTransacitonType(String transacitonType) {
		this.transacitonType = transacitonType;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
    
    
}