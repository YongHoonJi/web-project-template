package com.systrangroup.web.template.example.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.systrangroup.web.template.example.domain.Dept;
import com.systrangroup.web.template.example.domain.DeptNameType;

public interface DeptRepository extends PagingAndSortingRepository<Dept, Long> {
}
