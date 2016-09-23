package com.systrangroup.web.template.example.controller.dto;

import lombok.Data;

@Data
public class AuthenticationRevocation {
	
	private String token;
	private boolean isRevoke;
	
	public AuthenticationRevocation(String token){
		this.token = token;
	}
	
	public AuthenticationRevocation(boolean isRevoke, String token){
		this.isRevoke = isRevoke;
		this.token = token;
	}
}
