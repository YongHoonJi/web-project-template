package com.systrangroup.web.template.example.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.systrangroup.web.template.example.controller.dto.AuthenticationRequest;
import com.systrangroup.web.template.example.controller.dto.AuthenticationToken;

@Service
public class SecurityService{
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	
	public AuthenticationToken login(AuthenticationRequest authenticationRequest, HttpServletRequest request){
		// 사용자 정보 조회부분(인증)
		UserDetails details = this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword(), details.getAuthorities());
		
		this.authenticationManager.authenticate(token);
		
		System.out.println(token.isAuthenticated());
		if(token.isAuthenticated()){
			SecurityContextHolder.getContext().setAuthentication(token);
			System.out.println(String.format("Auto login %s successfully!", authenticationRequest.getEmail()));
		}
		// 사용자 권한 처리 부분
		final Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : details.getAuthorities()) {
			roles.put(authority.toString(), Boolean.TRUE);
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		String sessionId = session.getId();

		return new AuthenticationToken(details.getUsername(), roles, sessionId);
	}

}
