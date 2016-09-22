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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.systrangroup.web.template.example.domain.Greeting;
import com.systrangroup.web.template.example.domain.User;


@RestController
@RequestMapping("/mat/v1.0/auth")
public class AuthenticationController {
	
	@Autowired
	private TokenStore tokenStore;
	/**
	 * 인증을 전후 테스트를 위한 테스트 API
	 * 
	 */
	@RequestMapping("/peek")
	public Greeting peek(@AuthenticationPrincipal User user) {
		return new Greeting(100, "Hello world!");
	}
	
    /**
     * 인증 토큰을 토큰 저장소 (in memory에서 제거한다 = logout)
     * @param value 저장소에서 삭제할 토큰
     */
    @RequestMapping(value = "/revoke", method = RequestMethod.POST)
    public @ResponseBody void revoke(@RequestParam("token") String value) {
        System.out.println(this.revokeToken(value));
    }

    private boolean revokeToken(String tokenValue) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null) {
            return false;
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return true;
    }

}
