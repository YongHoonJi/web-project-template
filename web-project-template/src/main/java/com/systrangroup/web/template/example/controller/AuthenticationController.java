/*
 * Copyright 2014 the original author or authors.
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
package com.systrangroup.web.template.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systrangroup.web.template.example.controller.dto.AuthenticationRequest;
import com.systrangroup.web.template.example.controller.dto.AuthenticationToken;
import com.systrangroup.web.template.example.service.SecurityService;


/**
 * 인증용 API
 */
@RestController
@RequestMapping("/user")
public class AuthenticationController {
	
	@Autowired
	private SecurityService securityService;

	/**
	 * peek API
	 */
	@RequestMapping(value = "/test", method = { RequestMethod.GET })
	public void test(HttpServletRequest request) {
		System.out.println(request.getSession().getId());
		System.out.println(request.getUserPrincipal());
	}

	/**
	 * 사용자 인증 API
	 * @param authenticationRequest 인증 요청 정보
	 * @param request
	 * @return
	 * @throws ServletException 
	 */
	@RequestMapping(value = "/authenticate", method = { RequestMethod.GET })
	public AuthenticationToken login(
			@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {
		return this.securityService.login(authenticationRequest, request);
	}
}
