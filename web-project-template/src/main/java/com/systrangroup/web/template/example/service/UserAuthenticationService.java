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
package com.systrangroup.web.template.example.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.systrangroup.web.template.example.domain.User;
import com.systrangroup.web.template.example.repository.UserRepository;

/**
 * 유저 인증(DB 인증) 및 인증정보를 생성
 */
@Service
public class UserAuthenticationService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	public static final String ROLE_ADMIN = "ADMIN";
	
	public static final String ROLE_USER = "USER";

	/**
	 * 인증된 유저정보
	 */
	@SuppressWarnings("serial")
	static class SimpleUserDetails implements UserDetails {
		
		private String username;
		private String password;
		private boolean enabled = true;
		
		private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		public SimpleUserDetails(String username, String pw, String... extraRoles) {
			this.username = username;
			this.password = pw;

			// 롤 지정
			Set<String> roles = new HashSet<String>();
			roles.addAll(Arrays.<String>asList(null == extraRoles ? new String[0] : extraRoles));
			for (String r : roles) {
				authorities.add(new SimpleGrantedAuthority(role(r)));
			}
		}

		public String toString() {
			return "{enabled:" + isEnabled() + ", username:'" + getUsername() + "', password:'" + getPassword() + "'}";
		}

		@Override
		public boolean isEnabled() {
			return this.enabled;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return this.enabled;
		}

		@Override
		public boolean isAccountNonLocked() {
			return this.enabled;
		}

		@Override
		public boolean isAccountNonExpired() {
			return this.enabled;
		}

		@Override
		public String getUsername() {
			return this.username;
		}

		@Override
		public String getPassword() {
			return this.password;
		}

		private String role(String i) {
			return "ROLE_" + i;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.authorities;
		}
	}

	/*
	 * 유저 이름 기반으로 database 조회
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.getUserDetailByEmail(username);
	}
	
	/**
	 * 이메일 정보로 사용자 인증
	 * @param email 이메일
	 * @return
	 */
	private SimpleUserDetails getUserDetailByEmail(String email){
		System.out.println("email:"+email);
		Optional<User> ofUser = Optional.ofNullable(this.userRepository.findByEmail(email));
		if(ofUser.isPresent()) {
			return new SimpleUserDetails(ofUser.get().getEmail(), ofUser.get().getPassword(), ROLE_USER);
		}
		throw new UsernameNotFoundException("No user found for username " + email);
	}
}
