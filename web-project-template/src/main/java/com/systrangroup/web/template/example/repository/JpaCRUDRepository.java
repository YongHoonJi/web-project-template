package com.systrangroup.web.template.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.systrangroup.web.template.example.domain.User;

public interface JpaCRUDRepository extends CrudRepository<User, Long> {
	User findByName(String name);
}
