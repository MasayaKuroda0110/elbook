package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

/**
 * modelクラス
 */
@Entity
@Table(name = "users")
public class User{
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId")
	private Integer userId;
	
	@Column(name = "UserName",nullable = false,unique = true)
	private String userName;
	
	@Column(name = "Password",nullable = false)
	private String password;
	
	@Column(name = "Role",nullable = false,unique = true)
	private String role;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}