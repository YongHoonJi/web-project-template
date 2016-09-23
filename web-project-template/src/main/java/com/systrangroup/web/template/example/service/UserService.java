package com.systrangroup.web.template.example.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.mysema.query.types.Predicate;
import com.systrangroup.web.template.example.controller.dto.AuthenticationRevocation;
import com.systrangroup.web.template.example.domain.ActiveType;
import com.systrangroup.web.template.example.domain.Dept;
import com.systrangroup.web.template.example.domain.DeptNameType;
import com.systrangroup.web.template.example.domain.QDept;
import com.systrangroup.web.template.example.domain.User;
import com.systrangroup.web.template.example.repository.DeptDslRepository;
import com.systrangroup.web.template.example.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DeptDslRepository deptDslRepository;
	
	public Page<User> getAllUsers(Integer page, Integer size) {
		return this.userRepository.findAll(new PageRequest(page, size));
	}
	
	public User getUser(Long id){
		return this.userRepository.findOne(id);
	}
	
	public User createUser(User user){
		return this.userRepository.save(this.createUser(user.getName(), user.getAge(), user.getDept().getDeptNameType()));
	}
	
	public void updateUser(User user){
		this.userRepository.save(user);
	}
	
	public void deActivate(Long id){
		User user = this.getUser(id);
		user.setActiveType(ActiveType.N);
		this.userRepository.save(user);
	}
	
	private User createUser(String userName, int age, DeptNameType deptNameType){
		Predicate p = QDept.dept.deptNameType.eq(deptNameType);
		Dept dept = this.deptDslRepository.findOne(p);
    	User user = new User();
    	user.setAge(age);
    	user.setName(userName);
    	user.setActiveType(ActiveType.Y);
    	user.setDept(dept);
    	return user;
	}
	
}
