package com.hireasy.service.hireasyservice.models;

import com.hireasy.service.hireasyservice.entity.UserEntity;

public class AuthenticationResponse {

	private final String jwt;
	
	private final UserEntity user;

	public AuthenticationResponse(String jwt, UserEntity userEntity) {
		super();
		this.jwt = jwt;
		this.user = userEntity;
	}

	public String getJwt() {
		return jwt;
	}
	
	public UserEntity getUser() {
		return user;
	}
}
