package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 著者modelクラス
 */
@Entity
@Table(name = "authors")
@Data
public class Author {

	/** 著者ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_id")
	private Integer authorId;

	/** 著者名 */
	@Column(name = "name", nullable = false)
	private String name;

}