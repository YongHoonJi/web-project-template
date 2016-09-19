package com.systrangroup.web.template.example.repository;

import java.util.List;

import com.systrangroup.web.template.example.domain.User;

public interface UserRepositoryCustom {
	List<User> findInactiveUsers();
}
