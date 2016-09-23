package com.systrangroup.web.template.example.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.mysema.query.types.Predicate;
import com.systrangroup.web.template.example.controller.dto.AuthenticationRevocation;
import com.systrangroup.web.template.example.domain.ActiveType;
import com.systrangroup.web.template.example.domain.Dept;
import com.systrangroup.web.template.example.domain.DeptNameType;
import com.systrangroup.web.template.example.domain.QDept;
import com.systrangroup.web.template.example.domain.User;
import com.systrangroup.web.template.example.repository.DeptDslRepository;
import com.systrangroup.web.template.example.repository.UserRepository;

@Service
public class AuthenticationService {
	@Autowired
	private TokenStore tokenStore;
	
    public AuthenticationRevocation revokeToken(HttpServletRequest request) {
    	System.out.println(request.getHeader("Authorization"));
    	String token = this.fileterToken(request.getHeader("Authorization"));
    	System.out.println(token);
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (accessToken == null) {
            return new AuthenticationRevocation(false, token);
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return new AuthenticationRevocation(true, token);
    }
    
    private String fileterToken(String token){
    	return Strings.nullToEmpty(token).replace("Bearer ", "");
    }

}
