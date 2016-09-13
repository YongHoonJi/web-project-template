package com.systrangroup.web.template.example.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.systrangroup.web.template.example.domain.User;

public interface BusinessService {
	public List<User> findOneUserByNativeQuery();
	
	public List<User> findByNameFromUser(String name);
	
	public User findOneUserByAdvancedQuery(String name);
	
	public void saveUser();
	
	public List<User> findAllUser();
	
	@Transactional
	public User findOneUserByQueryDsl(Long id) throws Exception;
	
	public List<User> findListWithPagination(User user, Pageable pageable);
	
}
