package com.systrangroup.web.template.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.systrangroup.web.template.example.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	public Page<User> findAll(Pageable pageable);
	
	public List<User> findAllByName(String name, Pageable pageable);
	
}
