package com.systrangroup.web.template.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systrangroup.web.template.example.domain.User;

public interface AdvancedQueryRepository extends JpaRepository<User, Long> {
	public User findByName(String name);
	public User findByNameAndAge(String name, int age);
}