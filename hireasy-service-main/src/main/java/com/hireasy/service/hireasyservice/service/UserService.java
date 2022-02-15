package com.hireasy.service.hireasyservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hireasy.service.hireasyservice.entity.UserEntity;
import com.hireasy.service.hireasyservice.exception.CustomException;
import com.hireasy.service.hireasyservice.models.MyUserModel;
import com.hireasy.service.hireasyservice.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.getUserByEmail(email);
		if(userEntity != null) {
			return new MyUserModel(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
		} else {
			return null;
		}
	}
	
	public UserEntity getUserById(Long userId) throws CustomException {
		Optional<UserEntity> userOptional = userRepository.findById(userId);
		if(userOptional.isPresent()) {
			return userOptional.get();
		} else {
			throw new CustomException("404","No User found by Id - "+userId);
		}
	}

	public UserEntity getUserByEmail(String email) throws CustomException {
		UserEntity userEntity = userRepository.getUserByEmail(email);
		if(userEntity == null) {
			throw new CustomException("404","No User found by email - "+email);
		} else {
			return userEntity;
		}
	}

	public UserEntity save(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}


	public List<UserEntity> listAllUsers() {
		return userRepository.findAll();
	}

	public void deleteById(Long userId) throws CustomException {
		try{
			userRepository.deleteById(userId);
		} catch (Exception e){
			throw new CustomException("403","Unable to Delete user by Id - "+userId);
		}
	}

    public UserEntity getUserByEmailOrPhone(String userName) {
		List<UserEntity> users = userRepository.findByEmailOrPhoneNumber(userName, userName);

		if(users.size()>0){
			return users.get(0);
		} else {
			throw new CustomException("404","No user found with given email/phone");
		}
    }
}
