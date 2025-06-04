package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

/**
 * Userエンティティに対するDB操作を行うリポジトリ定義
 */
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUserName(String userName);
	User findByUserId(Integer userId);
}