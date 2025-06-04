package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * modelクラス
 */
@Entity
@Table(name = "authors")
@Data
public class Author{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_id")
	private Integer authorId;
	
	@Column(name = "name",nullable = false)
	private String name;
	
}