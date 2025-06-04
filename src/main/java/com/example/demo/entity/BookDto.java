package com.example.demo.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 入力されたデータを保持するクラス
 */
@Data
public class BookDto{
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String summary;
	
}