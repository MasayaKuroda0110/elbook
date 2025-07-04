package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.User;
import com.example.demo.repository.TransactionRepository;

/**
 * 貸出管理サービス
 */
@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * 書籍の登録
	 * @param book 書籍データ
	 */
	public void regBook(Book book) {
		Transaction transaction = new Transaction();
		transaction.setTransactionType("本棚");
		transaction.setBook(book);
		transactionRepository.save(transaction);
	}

	/**
	 * 書籍の貸出
	 * @param user 貸し出したユーザー
	 * @param book 書籍データ
	 * @param borrowDate 貸出日
	 * @return 貸出記録
	 */
	public Transaction borrowBook(User user, Book book, Date borrowDate) {
		Transaction existingTransaction = transactionRepository.findByBook(book);

		existingTransaction.setUser(user);
		existingTransaction.setTransactionType("貸出");
		existingTransaction.setBorrowDate(borrowDate);
		transactionRepository.save(existingTransaction);
		return existingTransaction;
	}

	/**
	 * 書籍の返却
	 * @param book 書籍データ
	 * @param returnDate 返却日
	 * @return 貸出記録
	 */
	public Transaction returnBook(Book book, Date returnDate) {
		Transaction existingTransaction = transactionRepository.findByBook(book);

		existingTransaction.setUser(null);
		existingTransaction.setTransactionType("本棚");
		existingTransaction.setReturnDate(returnDate);
		transactionRepository.save(existingTransaction);
		return existingTransaction;
	}

	/**
	 * 現在の貸出中か確認
	 * @param book 書籍データ
	 * @return true:貸出 false:本棚
	 */
	public boolean isBookCurrentlyBorrowed(Book book) {
		return transactionRepository.existsByBookAndReturnDateIsNull(book);
	}

	/**
	 * 特定の貸出管理の検索
	 * @param book 書籍データ
	 * @return 貸出記録
	 */
	public Transaction findTransaction(Book book) {
		return transactionRepository.findByBook(book);
	}

	/**
	 * すべての貸出管理の検索
	 * @return 全貸出記録
	 */
	public List<Transaction> findAllTransaction() {
		return transactionRepository.findAll();
	}
	
	public List<Transaction> findTransactionByUserIdTransactionType(User user){
		List<Transaction> lists = new ArrayList<>();
		for(Transaction transaction:transactionRepository.findByUser(user)) {
			if(transaction.getTransactionType().equals("貸出")) {
				lists.add(transaction);
			}
		}
		return lists;
	}

	/**
	 * 貸出記録の削除
	 * @param book 書籍データ
	 */
	public void deleteTransaction(Book book) {
		transactionRepository.delete(findTransaction(book));
	}

	/**
	 * 特定ユーザーの貸出書籍検索
	 * @param user ユーザー情報
	 * @param transactionType 貸出状況
	 * @return 貸出記録
	 */
	public List<Transaction> findByUserAndTransactionType(User user, String transactionType) {
		return transactionRepository.findByUserAndTransactionType(user, transactionType);
	}
}