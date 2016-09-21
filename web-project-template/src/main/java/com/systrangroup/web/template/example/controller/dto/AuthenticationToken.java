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
package com.systrangroup.web.template.example.controller.dto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

@Data
public class AuthenticationToken {

	private String email;
	private Map<String, Boolean> roles;
	private String token;

	public AuthenticationToken() {
	}

	public AuthenticationToken(String email, Map<String, Boolean> roles, String token) {
		this.roles = new ConcurrentHashMap<String, Boolean>(roles);
		this.token = token;
		this.email = email;
	}

}