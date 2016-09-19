package com.systrangroup.web.template.example;

import org.aspectj.lang.annotation.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)   
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=8888")
public class MainControllerTest {
	@Autowired 
	private WebApplicationContext wac;
	
	private MockMvc mock;
	
	@Before(value = "")
	public void setUp() throws Exception{
		System.out.println("init!");
		this.mock = MockMvcBuilders.webAppContextSetup(wac).build();
	}
}
