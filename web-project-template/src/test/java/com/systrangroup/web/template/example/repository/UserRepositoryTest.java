package com.systrangroup.web.template.example.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.systrangroup.web.template.example.Application;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class UserRepositoryTest {
	@Autowired
	private UserRepository respository;

	@Test
	public void findAll() {
		this.respository.findAll(new PageRequest(0, 10));
	}

	@Test
	public void findAllByName() {
		throw new RuntimeException("Test not implemented");
	}
}
