package com.systrangroup.web.template.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.systrangroup.web.template.example.domain.User;

// JpaRepository extends PagingAndSortingRepository , which in turn extends CrudRepository.
public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findAllByName(String name, Pageable pageable);
}
