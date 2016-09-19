package com.systrangroup.web.template.example.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;

import com.systrangroup.web.template.example.domain.Dept;

public interface DeptDslRepository extends Repository<Dept, Long>, QueryDslPredicateExecutor<Dept>{

}
