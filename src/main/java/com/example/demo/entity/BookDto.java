package com.example.demo.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 書籍登録入力データ保持クラス
 */
@Data
public class BookDto {

	/** タイトル */
	@NotEmpty
	private String title;

	/** 著者 */
	@NotEmpty
	private String name;

	/** あらすじ・概要 */
	@NotEmpty
	private String summary;

}