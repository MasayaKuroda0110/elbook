package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 書籍modelクラス
 */
@Entity
@Table(name = "books")
public class Book {

	/** 書籍ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Integer bookId;

	/** タイトル */
	@Column(name = "title")
	private String title;

	/** あらすじ・概要 */
	@Column(name = "summary")
	private String summary;

	/** 登録日 */
	@Column(name = "reg_date")
	private Date regDate = new Date();

	/** 著者 */
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "author_id")
	private Author author;

	/** 貸出 */
	@OneToOne(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Transaction transaction;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}