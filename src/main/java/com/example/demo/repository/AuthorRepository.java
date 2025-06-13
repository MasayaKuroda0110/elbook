package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Author;

/**
 * 著者リポジトリ
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	Optional<Author> findByName(String name);
}