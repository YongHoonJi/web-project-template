package com.systrangroup.web.template.example.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
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
import com.systrangroup.web.template.example.domain.Dept;
import com.systrangroup.web.template.example.domain.DeptNameType;
import com.systrangroup.web.template.example.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MainControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mock;

	private User existUser;

	private User createdUser;

	@Before
	public void setUp() throws Exception {
		System.out.println("init!");
		this.mock = MockMvcBuilders.webAppContextSetup(wac).build();
		this.existUser = this.getBeComparedUser();
		this.createdUser = this.CreateNewUser();
	}

	private User getBeComparedUser() {
		User user = new User();
		user.setId(new Long(2));
		user.setActiveType(ActiveType.Y);
		user.setAge(27);
		user.setName("JiYong");
		Dept dept = new Dept();
		dept.setDeptNameType(DeptNameType.CODER);
		user.setDept(dept);
		return user;
	}

	private User CreateNewUser() {
		User user = new User();
		user.setActiveType(ActiveType.Y);
		user.setAge(27);
		user.setName("JiYong");
		Dept dept = new Dept();
		dept.setDeptNameType(DeptNameType.ARCH);
		user.setDept(dept);
		return user;
	}

	@Test
	public void getUser() throws JsonProcessingException, Exception {
		mock.perform(MockMvcRequestBuilders.get("/sample/users/{id}", 2L))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(existUser.getName())));
	}

	@Test
	public void createUser() throws JsonProcessingException, Exception {
		mock.perform(MockMvcRequestBuilders.post("/sample/users")
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsBytes(createdUser)))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(createdUser.getName())));
	}

	@Test
	public void deleteUser() {
	}

	@Test
	public void getAllUsers() {
	}

	@Test
	public void updateUser() {
	}
}
