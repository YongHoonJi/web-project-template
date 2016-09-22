package com.systrangroup.web.template.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysema.query.types.Predicate;
import com.systrangroup.web.template.example.domain.ActiveType;
import com.systrangroup.web.template.example.domain.Dept;
import com.systrangroup.web.template.example.domain.DeptNameType;
import com.systrangroup.web.template.example.domain.QDept;
import com.systrangroup.web.template.example.domain.QUser;
import com.systrangroup.web.template.example.domain.User;
import com.systrangroup.web.template.example.repository.AdvancedQueryRepository;
import com.systrangroup.web.template.example.repository.DeptDslRepository;
import com.systrangroup.web.template.example.repository.JpaCRUDRepository;
import com.systrangroup.web.template.example.repository.QueryDslRepository;
import com.systrangroup.web.template.example.repository.QueryRepository;
import com.systrangroup.web.template.example.repository.SearchRepository;
import com.systrangroup.web.template.example.repository.UserRepository;
import com.systrangroup.web.template.example.repository.UserRepositoryCustom;

@Service
public class JpaTestService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DeptDslRepository deptDslRepository;
	
	@Autowired
	private JpaCRUDRepository jpaCRUDRepository;
	
	@Autowired
	private AdvancedQueryRepository advQueryRepository;
	
	@Autowired
	private QueryDslRepository queryDslRepository;
	
	@Autowired
	private SearchRepository searchRepository;
	
	@Autowired 
	private QueryRepository queryRepository;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;
	
	public List<User> findOneUserByNativeQuery() {
		return userRepositoryCustom.findInactiveUsers();
	}
	
	public List<User> findByNameFromUser(String name) {
        return queryRepository.findByName(name);
	}
	
	public User findOneUserByAdvancedQuery(String name){
		// search by name
		System.out.println(advQueryRepository.findByName(name).toString());
		int age = 99;
		// search by name and age
		System.out.println(advQueryRepository.findByNameAndAge(name, age));
		return advQueryRepository.findByName(name);
	}
	
	public void saveUser(){
    	jpaCRUDRepository.save(this.createMockUser("Steve", 27, DeptNameType.ARCH));
    	jpaCRUDRepository.save(this.createMockUser("Turing", 87, DeptNameType.CODER));
    	jpaCRUDRepository.save(this.createMockUser("Mick", 17, DeptNameType.QA));
    	jpaCRUDRepository.save(this.createMockUser("Cal", 27, DeptNameType.CODER));
    	jpaCRUDRepository.save(this.createMockUser("Ano", 22, DeptNameType.CODER));
	}
	
	private User createMockUser(String userName, int age, DeptNameType deptNameType){
		Predicate p = QDept.dept.deptNameType.eq(deptNameType);
		Dept dept = this.deptDslRepository.findOne(p);
    	User user = new User();
    	user.setAge(age);
    	user.setName(userName);
    	user.setActiveType(ActiveType.Y);
    	user.setDept(dept);
    	return user;
	}
	
	public User findOneUserByQueryDsl(Long id) throws Exception {
		User savedUser = jpaCRUDRepository.save(this.createMockUser("Jobs", 60, DeptNameType.CODER));

		Predicate p = QUser.user.id.eq(savedUser.getId()); // 1. select user 2. select dept
		Iterable<User> iUser = queryDslRepository.findAll(p);

		for (User it : iUser) {
			System.out.println(it.toString());
			System.out.println("dept name:" + it.getDept().getDeptNameType().toString());
		}

		// query with joined table
		User userDsl = searchRepository.joinResult(new Long(1));
		System.out.println(userDsl.toString());
		return userDsl;
	}
	
	public List<User> findListWithPagination(User user, Pageable pageable) {
		System.out.println(user.getName()+", "+pageable.getPageNumber()+"-"+pageable.getPageSize());
		return userRepository.findAllByName(user.getName(), pageable);
	}	
	
	public Page<User> getAllUsers(Integer page, Integer size) {
		return this.userRepository.findAll(new PageRequest(page, size));
	}
	
	public User getUser(Long id){
		return this.userRepository.findOne(id);
	}
	
	public User createUser(User user){
		return this.userRepository.save(this.createMockUser(user.getName(), user.getAge(), user.getDept().getDeptNameType()));
	}
	
	public void updateUser(User user){
		this.userRepository.save(user);
	}
	
	public void deActivate(Long id){
		User user = this.getUser(id);
		user.setActiveType(ActiveType.N);
		this.userRepository.save(user);
	}

}
