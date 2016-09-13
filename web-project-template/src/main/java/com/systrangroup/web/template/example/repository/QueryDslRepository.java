package com.systrangroup.web.template.example.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;

import com.systrangroup.web.template.example.domain.User;

public interface QueryDslRepository extends Repository<User, Long>, QueryDslPredicateExecutor<User>{

}
