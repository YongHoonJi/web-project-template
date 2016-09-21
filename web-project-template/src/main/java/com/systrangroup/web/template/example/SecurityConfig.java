/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.systrangroup.web.template.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.systrangroup.web.template.example.service.UserAuthenticationService;

/**
 * spring security 설정 클래스
 */
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserAuthenticationService userAuthService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CSRF 공격 차단 : https://ko.wikipedia.org/wiki/%EC%82%AC%EC%9D%B4%ED%8A%B8_%EA%B0%84_%EC%9A%94%EC%B2%AD_%EC%9C%84%EC%A1%B0
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		http.authorizeRequests()
		.antMatchers("/user/authenticate", "/user/test").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/sample/users").permitAll()
		.anyRequest().authenticated()
		//.and().httpBasic()
		.and().logout().permitAll();
		
		
		// 권한에 따른 접근 API 설정
		//http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/plants/**").hasRole(UserAuthenticationService.ROLE_ADMIN);
		//http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/plants", "/api/plants/**").hasRole(UserAuthenticationService.ROLE_ADMIN);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.userDetailsService(userAuthService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}

