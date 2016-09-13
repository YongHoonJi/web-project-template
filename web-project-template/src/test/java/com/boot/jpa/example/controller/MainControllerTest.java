package com.boot.jpa.example.controller;

import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.google.common.collect.Lists;
import com.mysema.query.types.Predicate;
import com.systrangroup.web.template.example.DemoBootApplication;
import com.systrangroup.web.template.example.domain.QUser;
import com.systrangroup.web.template.example.domain.User;
import com.systrangroup.web.template.example.repository.QueryDslRepository;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)   
@SpringApplicationConfiguration(classes = DemoBootApplication.class)
@WebAppConfiguration   
@IntegrationTest("server.port:8888")
public class MainControllerTest {
	@Autowired private QueryDslRepository queryDslRepository;
	private MockMvc mock;
	
	@Before(value = "")
	public void init() throws Exception{
		System.out.println("init!");
	}
	
	@Test
    public void geUserByQueryDsl() {
    	Predicate p = QUser.user.name.eq("coder");
    	List<User> list = Lists.newArrayList(queryDslRepository.findAll(p));
    	Assert.assertEquals(list.size(), 3);
    }

}
