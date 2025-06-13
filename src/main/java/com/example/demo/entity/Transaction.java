package com.example.demo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 貸出管理modelクラス
 */
@Entity
@Table(name = "Transactions")
public class Transaction {

	/** 貸出ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private int transactionID;

	/** 貸出状況 */
	@Column(name = "transaction_type")
	private String transactionType;

	/** 書籍 */
	@OneToOne
	@JoinColumn(name = "book_id", nullable = false)
	@JsonIgnore
	private Book book;

	/** ユーザー */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/** 貸出日 */
	@Column(name = "borrow_date")
	private Date borrowDate;

	/** 返却日 */
	@Column(name = "return_date")
	private Date returnDate;

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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