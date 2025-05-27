package com.example.demo.entity;

import jakarta.validation.constraints.NotEmpty;

/**
 * 入力データを保持するクラス
 */
public class UserDto{
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}