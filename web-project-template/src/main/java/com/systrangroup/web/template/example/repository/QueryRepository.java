package com.systrangroup.web.template.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.systrangroup.web.template.example.domain.User;

public interface QueryRepository extends JpaRepository<User, Long> {
	@Query(value="select u from User u where u.name=?", nativeQuery = false)
	List<User> findByName(String name);
}
