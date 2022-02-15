package com.hireasy.service.hireasyservice.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUserModel extends User {

	private static final long serialVersionUID = 1L;

	public MyUserModel(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
}
