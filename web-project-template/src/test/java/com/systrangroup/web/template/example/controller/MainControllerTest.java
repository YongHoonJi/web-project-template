package com.systrangroup.web.template.example.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systrangroup.web.template.example.Application;
import com.systrangroup.web.template.example.domain.ActiveType;
import com.systrangroup.web.template.example.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MainControllerTest {
	
	@Autowired 
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mock;
	
	private User user;

	@Before
	public void setUp() throws Exception {
		System.out.println("init!");
		this.mock = MockMvcBuilders.webAppContextSetup(wac).build();
		user = new User();
		user.setId(new Long(2));
		user.setActiveType(ActiveType.N);
		user.setAge(27);
		user.setName("JiYong");
	}
	
	@Test
	public void getUser() throws JsonProcessingException, Exception {
        ResultActions resultActions =
                mock.perform(MockMvcRequestBuilders.get("/sample/users/{id}", 2L));
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(user.getName())));
    }
/*
	public void createUser() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void deleteUser() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getAllUsers() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void updateUser() {
		throw new RuntimeException("Test not implemented");
	}*/
}
